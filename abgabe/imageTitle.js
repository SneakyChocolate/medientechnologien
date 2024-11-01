let images = document.getElementsByClassName("img1")

for (let i = 0; i < images.length; i ++) {
    let p = document.createElement("p");
    let src = images[i].src;
    p.innerText = src.substring(src.lastIndexOf('/'));
    p.style.position = "relative";
    images[i].before(p);
}
