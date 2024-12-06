#!/bin/bash

function run() {
  # $1 = name, $2 = sample, $3 = modification
  javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/ue5-audios/$1.wav ../abgabe/ue5-outputs/$1 $2 $3
}

function run_amplificate() {
  run $1 amplificate 3 &&\
  run $1 amplificate 6 &&\
  run $1 amplificate 9 &&\
  run $1 amplificate 12
}

run_amplificate Musik_Gruppe4 &&\
run_amplificate Noise_08 &&\
run_amplificate Sprache_Gruppe4 &&\
run_amplificate sine_1k_08


ls ../abgabe/ue5-outputs

