readrgb() {
  gradle run --args="../../abgabe/pics/$1.bmp"
}

gradlerun() {
  gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1_$2_$3.bmp $2 $3"
}

