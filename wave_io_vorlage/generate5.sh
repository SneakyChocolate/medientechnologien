#!/bin/bash

function compile() {
	javac -d out $(find . -name "*.java")
}
function run() {
	# $1 = name, $2 = sample, $3 = modification
	java -cp out/ wave_io ../abgabe/ue5-audios/$1.wav ../abgabe/ue5-outputs/$1 $2 $3
}

compile

read -p "compiled. press enter to run..."

function run_amplificate() {
	run $1 amplificate "3" &&\
	run $1 amplificate "6" &&\
	run $1 amplificate "9" &&\
	run $1 amplificate "12" &&\
	run $1 amplificate "20" &&\
	run $1 amplificate "50" &&\
	run $1 amplificate "-20"
}
function run_echo() {
	run $1 echo "10" &&\
	run $1 echo "100" &&\
	run $1 echo "200" &&\
	run $1 echo "1000" &&\
	run $1 echo "2000"
}

function run_filter() {
	run $1 filter_p
	run $1 filter_n
}

run_amplificate Musik_Gruppe4 &&\
run_amplificate Noise_08 &&\
run_amplificate Sprache_Gruppe4 &&\
run_amplificate sine_1k_08

run_echo Musik_Gruppe4 &&\
run_echo Noise_08 &&\
run_echo Sprache_Gruppe4 &&\
run_echo sine_1k_08

run_filter Musik_Gruppe4 &&\
run_filter Noise_08 &&\
run_filter Sprache_Gruppe4 &&\
run_filter sine_1k_08

ls ../abgabe/ue5-outputs

