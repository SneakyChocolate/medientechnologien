readrgb() {
  gradle run --args="../../abgabe/pics/$1.bmp"
}

gradlerun() {
    IMAGE_NAME=$1
    IMAGE_CHANGE=$2
    IMAGE_CHANGE_VALUE=$3
    OUTDIR=$4
    if test "$#" -eq 1; then
        gradle run --args="../../abgabe/pics/${IMAGE_NAME}.bmp ../../abgabe/output-images/${IMAGE_NAME}.bmp"
    elif test "$#" -eq 2; then
        gradle run --args="../../abgabe/pics/${IMAGE_NAME}.bmp ../../abgabe/output-images/${IMAGE_NAME}_${IMAGE_CHANGE}.bmp ${IMAGE_CHANGE}"
    elif test "$#" -eq 3; then
        gradle run --args="../../abgabe/pics/${IMAGE_NAME}.bmp ../../abgabe/output-images/${IMAGE_NAME}_${IMAGE_CHANGE}_${IMAGE_CHANGE_VALUE}.bmp ${IMAGE_CHANGE} ${IMAGE_CHANGE_VALUE}"
    elif test "$#" -eq 4; then
        gradle run --args="../../abgabe/pics/${IMAGE_NAME}.bmp ../../abgabe/${OUTDIR}/${IMAGE_NAME}_${IMAGE_CHANGE}_${IMAGE_CHANGE_VALUE}.bmp ${IMAGE_CHANGE} ${IMAGE_CHANGE_VALUE}"
    fi
}

