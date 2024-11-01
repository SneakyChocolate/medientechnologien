readrgb() {
  gradle run --args="../../abgabe/pics/$1.bmp"
}

gradlerun() {
  gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1$2.bmp $2"
}

