#!/bin/bash
cd wave_io_vorlage &&\

function run() {
  # $1 = name, $2 = sample
  javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/$1.wav ../abgabe/output/$1 $2
}

run Musik_Gruppe4 downsample &&\
run Sprache_Gruppe4 downsample &&\
run sine_hi05 downsample &&\
run sine_lo05 downsample &&\
run Musik_Gruppe4 bitred &&\
run Sprache_Gruppe4 bitred &&\
run sine_hi05 bitred &&\
run sine_lo05 bitred &&\
run Musik_Gruppe4 bitreddif &&\
run Sprache_Gruppe4 bitreddif &&\
run sine_hi05 bitreddif &&\
run sine_lo05 bitreddif

ls ../abgabe/output
