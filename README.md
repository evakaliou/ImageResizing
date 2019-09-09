# ImageResizing
Uses dynamic programming to efficiently reduce the width of an image in a content-aware manner, by iteratively finding and removing minimum content vertical paths. 

Input Arguments:  java Driver [imageName] [extension] [desiredWidth]

Comments: [imageName] shouldn't include extension. [extension] shouldn't include dot. 

Example Run: java Driver crater jpg 4000


Got the idea of coding this after seeing: https://avikdas.com/2019/05/14/real-world-dynamic-programming-seam-carving.html 
