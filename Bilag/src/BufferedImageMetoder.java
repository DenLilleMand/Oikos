import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class BufferedImageMetoder {
	
	//Changes the size of a buffered image.
	public static BufferedImage reScaleImage(BufferedImage bufBefore, double scale)
	{
		int w = bufBefore.getWidth();
		int h = bufBefore.getHeight();
		
		// TYPE_INT_ARGB Represents an image with 8-bit RGBA color components packed into integer pixels
		BufferedImage bufAfter = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
		
		//The AffineTransform class represents a 2D affine transform that performs a linear mapping from
		//2D coordinates to other 2D coordinates that preserves the "straightness" and "parallelness" of lines
		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		
		//This class uses an affine transform to perform a linear mapping from 2D coordinates in the source image or
		//Raster to 2D coordinates in the destination image or Raster
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		
		//scaleOp() Transforms the source BufferedImage and stores the results in the destination BufferedImage
		bufAfter = scaleOp.filter(bufBefore, bufAfter);
		return bufAfter;
	}
	
	//makes the bufferedImage transparent
	public static BufferedImage makeTransparent(BufferedImage image) 
	{
		// TYPE_INT_ARGB Represents an image with 8-bit RGBA color components packed into integer pixels
		BufferedImage temp = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				
				//getRGB returns an integer pixel in the default RGB color model (TYPE_INT_ARGB) and default sRGB colorspace
				if (image.getRGB(i, j) != image.getRGB(0, 0)) {
					//setRGB() sets a pixel in this BufferedImage to the specified RGB value.
					temp.setRGB(i, j, image.getRGB(i, j));
				}
			}
		}
		return temp;
	}
}
