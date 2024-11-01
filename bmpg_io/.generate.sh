
source .gradlerun.sh

gradlerun Nature_Gruppe4 graustufen
gradlerun Manmade_Gruppe4 graustufen

for i in $(seq 2 10); do
    gradlerun Nature_Gruppe4 downsampling $i
    gradlerun Manmade_Gruppe4 downsampling $i
done

for i in $(seq 1 8); do
    gradlerun Nature_Gruppe4 bitreducing $i
    gradlerun Manmade_Gruppe4 bitreducing $i

    gradlerun Nature_Gruppe4 bitreducingdif $i
    gradlerun Manmade_Gruppe4 bitreducingdif $i
done
