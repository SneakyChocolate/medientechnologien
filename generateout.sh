#!/bin/bash
cd wave_io_vorlage &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/Musik_Gruppe4.wav ../abgabe/output/Musik_Gruppe4 downsampled &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/Sprache_Gruppe4.wav ../abgabe/output/Sprache_Gruppe4 downsampled &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/sine_hi05.wav ../abgabe/output/sine_hi05 downsampled &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/sine_lo05.wav ../abgabe/output/sine_lo05 downsampled

javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/Musik_Gruppe4.wav ../abgabe/output/Musik_Gruppe4 bitred &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/Sprache_Gruppe4.wav ../abgabe/output/Sprache_Gruppe4 bitred &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/sine_hi05.wav ../abgabe/output/sine_hi05 bitred &&\
javac -d out $(find . -name "*.java") && java -cp out/ wave_io ../abgabe/audio/sine_lo05.wav ../abgabe/output/sine_lo05 bitred

ls ../abgabe/output
