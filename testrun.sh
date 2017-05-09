#!/bin/bash
WEKAJAR=/Applications/weka-3-6-15-oracle-jvm.app/Contents/Java/weka.jar
JVMARGS="-XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=10s,filename="
OUTPUTDIR="/Users/luca/Desktop/out"


GRIDPARAMS=("1" "10" "1" "0.1" "0.5" "0.01")

cd bin/

declare -a CLASSES=("mainHW/MainHW" "mainHW_sequential/MainHW" "mainBaseline/Main")
declare -a DATASETS=("iris" "wine-quality" "magic04")

for TESTCLASS in "${CLASSES[@]}"
do
    echo $TESTCLASS
    FILENAME=$OUTPUTDIR/`dirname $TESTCLASS`
    for DATASET in "${DATASETS[@]}"
    do
        java $JVMARGS$FILENAME-$DATASET.jfr -cp $WEKAJAR:. $TESTCLASS "../data/$DATASET.arff" ${GRIDPARAMS[@]} >> $FILENAME.txt
    done
done