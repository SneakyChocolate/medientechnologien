#!/bin/bash
cd wave_io_vorlage &&\

function run() {
  # $1 = name, $2 = sample
  javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/$1.wav ../abgabe/output/$1 $2
}

function runall() {
  # $1 = name
  run $1 downsample &&\
  run $1 bitred &&\
  run $1 bitreddif
}

runall Musik_Gruppe4 &&\
runall Sprache_Gruppe4 &&\
runall sine_hi05 &&\
runall sine_lo05

ls ../abgabe/output
