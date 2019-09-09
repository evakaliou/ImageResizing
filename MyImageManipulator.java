
public class MyImageManipulator{

    public void cleverWidthReduction(MyImage imageObject,int desiredWidth){
        
    	//will store the path to delete:
    	int[] path = new int[imageObject.getHeight()];;
		//will store for each pixel, the content of the min content path... 
		//...starting from the top and ending at that pixel:
    	Double opt[][];
    	//will store the current content matrix:
    	Double image[][];
    	//helpers for finding min value:
    	Double minValue;
    	int minIndex;
    	//helpers for back traversal of opt:
    	Double left;
		Double middle;
		Double right;
		
		//loop removes the current minimum content path, until desiredWidth is reached:
    	while(imageObject.getWidth() != desiredWidth){

    		opt = new Double[imageObject.getHeight()][imageObject.getWidth()];
    		image = imageObject.getContentMatrix();
			
			//recursion base case; first row:
			for(int col=0; col<imageObject.getWidth(); col++){
				opt[0][col] = image[0][col];
			} 
			
			//recursion; following rows:	
    		for(int row=1; row<imageObject.getHeight(); row++) {
    			for(int col=0; col<imageObject.getWidth(); col++) {
					//left edge
    				if(col==0){
    					opt[row][col] = image[row][col] + Math.min(opt[row-1][col], opt[row-1][col+1]);
    				}
    				//right edge
    				else if(col==imageObject.getWidth()-1) {
    					opt[row][col] = image[row][col] + Math.min(opt[row-1][col-1], opt[row-1][col]);
					}
					//"normal"
    				else {
    					opt[row][col] = image[row][col] + Math.min(Math.min(opt[row-1][col-1], opt[row-1][col]), opt[row-1][col+1]);
    				}
    			}
			}
			
			//finds end of minimum content path that ends in leftmost pixel:
			minValue = opt[imageObject.getHeight()-1][0];
			minIndex = 0;
			for(int col=1; col<imageObject.getWidth();col++){
				if(opt[imageObject.getHeight()-1][col]<minValue){
					minValue = opt[imageObject.getHeight()-1][col];
					minIndex = col;
				}
			}
			//stores end of path:
			path[imageObject.getHeight()-1] = minIndex;

    		//traverse opt backwards to find min path; tie break to the left:
    		for(int row=imageObject.getHeight()-1; row>0; row--) {
    			//left edge
    			if(minIndex == 0) {
	    			middle = opt[row-1][minIndex];
					right = opt[row-1][minIndex+1];
					if(right<middle) minIndex++;
    			}
    			//right edge
    			else if(minIndex == imageObject.getWidth() - 1) {
    				left = opt[row-1][minIndex-1];
	    			middle = opt[row-1][minIndex];
					if(left<=middle) minIndex--;
    			}
    			//"normal"
    			else {
	    			left = opt[row-1][minIndex-1];
					middle = opt[row-1][minIndex];
					right = opt[row-1][minIndex+1];
					minValue = opt[row][minIndex]-image[row][minIndex];
					if(left==minValue) minIndex--;
					else if(right<middle) minIndex++;
				}
				path[row-1] = minIndex;
    		}
    		imageObject.deleteVerticalPath(path);
		}
		//END of path removal loop
    }
	//END of cleverWidthReduction function
}