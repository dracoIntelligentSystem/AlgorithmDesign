#!/bin/bash

# NEEDS THE FOLLOWING PROGRAMS: xmlstarlet, java.

# This script takes a JFR file as input and outputs a string containing relevant
# data stored in it:
# * Name of the test class (mainHW, mainHW_sequential or mainBaseline) 
# * Name of the dataset
# * Grid parameters
# * Wall-clock time of the test
# * Average CPU usage
# * Average and max heap usage

# Notice that average CPU usage may return 0. This means that no CPU samples have
# been recorded (due to a very short execution time, for instance).

# Convert JFR file to XML
BASENAME=`basename "$1" .jfr`
XML=$BASENAME.xml
java oracle.jrockit.jfr.parser.Parser -xml $1 > $XML

# Namespace URIs for xmlstarlet
JVMURL='http://www.oracle.com/hotspot/jvm/'
JFRURL='http://www.oracle.com/hotspot/jvm/'

# Get arguments (type of test, dataset, parameters for the grid)
ARGS=`xmlstarlet sel -N jvm="$JVMURL" -N jfr="$JFRURL" -t -v "//jvm:javaArguments/text()" "$XML"`
# Put them into a bash array
read -ra ARR <<< "$ARGS"

DATASET=`basename "${ARR[1]}" .arff`

# Get elapsed time
# Get endtime from XML, put it in proper ISO format, and then to
# Unix timestamp. Do the same for starttime and compute difference
ENDTIMESTAMP=`xmlstarlet sel -N jvm="$JVMURL" -N jfr="$JFRURL" -t -v "//jfr:chunk/@jfr:endTime" $XML | sed 's/^\(....\)\(..\)\(..\)-\(..\)\(..\)\(..\)\(.*\)/\1-\2-\3 \4:\5:\6,\7/g'`
ENDTIMESTAMP=`gdate -d "$ENDTIMESTAMP" +"%s.%N"`
STARTTIMESTAMP=`xmlstarlet sel -N jvm="$JVMURL" -N jfr="$JFRURL" -t -v "//jfr:chunk/@jfr:startTime" $XML | sed 's/^\(....\)\(..\)\(..\)-\(..\)\(..\)\(..\)\(.*\)/\1-\2-\3 \4:\5:\6,\7/g'`
STARTTIMESTAMP=`gdate -d "$STARTTIMESTAMP" +"%s.%N"`
TIME=`echo $ENDTIMESTAMP - $STARTTIMESTAMP | bc`

# Average CPU usage
xmlstarlet sel -N jvm="$JVMURL" -N jfr="$JFRURL" -t -v "//jvm:os_processor_cpu_load/jvm:machineTotal/text()" "$XML" > "$BASENAME-cpu.txt"
CPUAVG=`awk '{s+=$1} END {print s/NR}' "$BASENAME-cpu.txt"`
rm "$BASENAME-cpu.txt"

# Get used heap stats.
xmlstarlet sel -N jvm="$JVMURL" -N jfr="$JFRURL" -t -v "//jvm:vm_gc_heap_summary/jvm:heapUsed/text()" "$XML" > "$BASENAME-heap.txt"
HEAPAVG=`awk '{s+=$1} END {printf("%i",s/NR)}' "$BASENAME-heap.txt"`
HEAPMAX=`sort -nr "$BASENAME-heap.txt" | head -n 1`
rm "$BASENAME-heap.txt"

# Delete XML file and print results
rm "$XML"
echo ${ARR[0]},$DATASET,${ARR[2]},${ARR[3]},${ARR[4]},${ARR[5]},${ARR[6]},${ARR[7]},$TIME,$CPUAVG,$HEAPMAX,$HEAPAVG