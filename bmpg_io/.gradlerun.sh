readrgb() {
  gradle run --args="../../abgabe/pics/$1.bmp"
}

# $1 = image name
# $2 = image change
# $3 = image change value
# $4 = folder name
gradlerun() {
    if test "$#" -eq 1; then
        gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1.bmp"
    elif test "$#" -eq 2; then
        gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1_$2.bmp $2"
    elif test "$#" -eq 3; then
        gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/output-images/$1_$2_$3.bmp $2 $3"
    elif test "$#" -eq 4; then
        gradle run --args="../../abgabe/pics/$1.bmp ../../abgabe/$4/$1_$2_$3.bmp $2 $3"
    fi
}

