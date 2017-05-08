#!/bin/bash
WEKAJAR=/Applications/weka-3-6-15-oracle-jvm.app/Contents/Java/weka.jar
JVMARGS="-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=10s,filename="
OUTPUTDIR="/Users/luca/Desktop"


GRIDPARAMS="1 10 1 0.1 0.5 0.01"

cd bin/

# for i in {1..5}
# do
#     FILENAME="filename=$OUTPUTDIR/mainHW$i.jfr"
#     java $JVMARGS$FILENAME -cp $WEKAJAR:. mainHW/MainHW "../data/iris.arff" 1 10 1 0.1 0.5 0.01 >> $OUTPUTDIR/mainHW.txt
# done

FILENAME="$OUTPUTDIR/mainHW"
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW/MainHW "../data/iris.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW/MainHW "../data/magic.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW/MainHW "../data/wine-qualities.arff" $GRIDPARAMS >> $FILENAME.txt

FILENAME="$OUTPUTDIR/mainHW_sequential"
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW_sequential/MainHW "../data/iris.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW_sequential/MainHW "../data/magic.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainHW_sequential/MainHW "../data/wine-qualities.arff" $GRIDPARAMS >> $FILENAME.txt
FILENAME="$OUTPUTDIR/mainBaseline"
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainBaseline/Main "../data/iris.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainBaseline/Main "../data/magic.arff" $GRIDPARAMS >> $FILENAME.txt
java $JVMARGS$FILENAME.jfr -cp $WEKAJAR:. mainBaseline/Main "../data/wine-qualities.arff" $GRIDPARAMS >> $FILENAME.txt