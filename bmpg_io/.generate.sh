
gradlerun() {
  gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1$2.bmp $2"
}

gradlerun Nature_Gruppe4 graustufen
gradlerun Manmade_Gruppe4 graustufen

gradlerun Nature_Gruppe4 downsampling
gradlerun Manmade_Gruppe4 downsampling

gradlerun Nature_Gruppe4 bitreducing
gradlerun Manmade_Gruppe4 bitreducing

gradlerun Nature_Gruppe4 bitreducingdif
gradlerun Manmade_Gruppe4 bitreducingdif
