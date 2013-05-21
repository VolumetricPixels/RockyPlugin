/*
 * This file is part of RockyPlugin.
 *
 * Copyright (c) 2012-2013, VolumetricPixels <http://www.volumetricpixels.com/>
 * RockyPlugin is licensed under the GNU Lesser General Public License.
 *
 * RockyPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RockyPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.volumetricpixels.rockyapi.math;

/**
 * <code>Color</code> defines a color made from a collection of red, green and
 * blue values. An alpha value determines is transparency. All values must be
 * between 0 and 1. If any value is set higher or lower than these constraints
 * they are clamped to the min or max. That is, if a value smaller than zero is
 * set the value clamps to zero. If a value higher than 1 is passed, that value
 * is clamped to 1. However, because the attributes r, g, b, a are public for
 * efficiency reasons, they can be directly modified with invalid values. The
 * client should take care when directly addressing the values. A call to clamp
 * will assure that the values are within the constraints.
 */
public class Color {

	/**
	 * the color black (0,0,0).
	 */
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	/**
	 * the color white (1,1,1).
	 */
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	/**
	 * the color gray (.2,.2,.2).
	 */
	public static final Color DARK_GRAY = new Color(0.2f, 0.2f, 0.2f, 1.0f);
	/**
	 * the color gray (.5,.5,.5).
	 */
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	/**
	 * the color gray (.8,.8,.8).
	 */
	public static final Color LIGHT_GRAY = new Color(0.8f, 0.8f, 0.8f, 1.0f);
	/**
	 * the color red (1,0,0).
	 */
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	/**
	 * the color green (0,1,0).
	 */
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	/**
	 * the color blue (0,0,1).
	 */
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	/**
	 * the color yellow (1,1,0).
	 */
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	/**
	 * the color magenta (1,0,1).
	 */
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 1.0f, 1.0f);
	/**
	 * the color cyan (0,1,1).
	 */
	public static final Color CYAN = new Color(0.0f, 1.0f, 1.0f, 1.0f);
	/**
	 * the color orange (251/255, 130/255,0).
	 */
	public static final Color ORANGE = new Color(251.0f / 255.0f, 130.0f / 255.0f, 0.0f,
			1f);
	/**
	 * the color brown (65/255, 40/255, 25/255).
	 */
	public static final Color BROWN = new Color(65.0f / 255.0f, 40.0f / 255.0f,
			25f / 255f, 1f);
	/**
	 * the color pink (1, 0.68, 0.68).
	 */
	public static final Color PINK = new Color(1.0f, 0.68f, 0.68f, 1.0f);
	/**
	 * the black color with no alpha (0, 0, 0, 0);
	 */
	public static final Color BLACK_NO_ALPHA = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	/**
	 * The red component of the color.
	 */
	private float r;
	/**
	 * The green component of the color.
	 */
	private float g;
	/**
	 * the blue component of the color.
	 */
	private float b;
	/**
	 * the alpha component of the color. 0 is transparent and 1 is opaque
	 */
	private float a;
	
	/**
	 * 
	 * @param floatBits
	 */
	public Color(float floatBits) {
		int color = Float.floatToIntBits(floatBits);
		
		this.a = (255 * color) << 24;
		this.b = (255 * color) << 16;
		this.g = (255 * color) << 8;
		this.r = (255 * color);
	}

	/**
	 * Constructor instantiates a new <code>ColorRGBA</code> object. This color
	 * is the default "white" with all values 1.
	 * 
	 */
	public Color() {
		this.r = 1.0f;
		this.g = 1.0f;
		this.b = 1.0f;
		this.a = 1.0f;
	}

	/**
	 * Constructor instantiates a new <code>ColorRGBA</code> object. The values
	 * are defined as passed parameters. These values are then clamped to insure
	 * that they are between 0 and 1.
	 * 
	 * @param r
	 *            the red component of this color.
	 * @param g
	 *            the green component of this color.
	 * @param b
	 *            the blue component of this color.
	 * @param a
	 *            the alpha component of this color.
	 */
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * Copy constructor creates a new <code>ColorRGBA</code> object, based on a
	 * provided color.
	 * 
	 * @param rgba
	 *            the <code>ColorRGBA</code> object to copy.
	 */
	public Color(Color rgba) {
		this.a = rgba.a;
		this.r = rgba.r;
		this.g = rgba.g;
		this.b = rgba.b;
	}

	/**
	 * 
	 * <code>set</code> sets the RGBA values of this color. The values are then
	 * clamped to insure that they are between 0 and 1.
	 * 
	 * @param r
	 *            the red component of this color.
	 * @param g
	 *            the green component of this color.
	 * @param b
	 *            the blue component of this color.
	 * @param a
	 *            the alpha component of this color.
	 */
	public void set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	/**
	 * <code>set</code> sets the values of this color to those set by a
	 * parameter color.
	 * 
	 * @param rgba
	 *            ColorRGBA the color to set this color to.
	 * @return this
	 */
	public Color set(Color rgba) {
		if (rgba == null) {
			r = 0;
			g = 0;
			b = 0;
			a = 0;
		} else {
			r = rgba.r;
			g = rgba.g;
			b = rgba.b;
			a = rgba.a;
		}
		return this;
	}

	/**
	 * <code>clamp</code> insures that all values are between 0 and 1. If any
	 * are less than 0 they are set to zero. If any are more than 1 they are set
	 * to one.
	 * 
	 */
	public void clamp() {
		if (r < 0) {
			r = 0;
		} else if (r > 1) {
			r = 1;
		}

		if (g < 0) {
			g = 0;
		} else if (g > 1) {
			g = 1;
		}

		if (b < 0) {
			b = 0;
		} else if (b > 1) {
			b = 1;
		}

		if (a < 0) {
			a = 0;
		} else if (a > 1) {
			a = 1;
		}
	}

	/**
	 * 
	 * <code>getColorArray</code> retrieves the color values of this object as a
	 * four element float array.
	 * 
	 * @return the float array that contains the color elements.
	 */
	public float[] getColorArray() {
		return new float[] { r, g, b, a };
	}

	/**
	 * Stores the current r/g/b/a values into the tempf array. The tempf array
	 * must have a length of 4 or greater, or an array index out of bounds
	 * exception will be thrown.
	 * 
	 * @param store
	 *            The array of floats to store the values into.
	 * @return The float[] after storage.
	 */
	public float[] getColorArray(float[] store) {
		store[0] = r;
		store[1] = g;
		store[2] = b;
		store[3] = a;
		return store;
	}

	/**
	 * Sets this color to the interpolation by changeAmnt from this to the
	 * finalColor this=(1-changeAmnt)*this + changeAmnt * finalColor
	 * 
	 * @param finalColor
	 *            The final color to interpolate towards
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from this towards finalColor
	 */
	public void interpolate(Color finalColor, float changeAmnt) {
		this.r = (1 - changeAmnt) * this.r + changeAmnt * finalColor.r;
		this.g = (1 - changeAmnt) * this.g + changeAmnt * finalColor.g;
		this.b = (1 - changeAmnt) * this.b + changeAmnt * finalColor.b;
		this.a = (1 - changeAmnt) * this.a + changeAmnt * finalColor.a;
	}

	/**
	 * Sets this color to the interpolation by changeAmnt from beginColor to
	 * finalColor this=(1-changeAmnt)*beginColor + changeAmnt * finalColor
	 * 
	 * @param beginColor
	 *            The begining color (changeAmnt=0)
	 * @param finalColor
	 *            The final color to interpolate towards (changeAmnt=1)
	 * @param changeAmnt
	 *            An amount between 0.0 - 1.0 representing a precentage change
	 *            from beginColor towards finalColor
	 */
	public void interpolate(Color beginColor, Color finalColor, float changeAmnt) {
		this.r = (1 - changeAmnt) * beginColor.r + changeAmnt * finalColor.r;
		this.g = (1 - changeAmnt) * beginColor.g + changeAmnt * finalColor.g;
		this.b = (1 - changeAmnt) * beginColor.b + changeAmnt * finalColor.b;
		this.a = (1 - changeAmnt) * beginColor.a + changeAmnt * finalColor.a;
	}

	/**
	 * Multiplies each r/g/b/a of this color by the r/g/b/a of the given color
	 * and returns the result as a new ColorRGBA. Used as a way of combining
	 * colors and lights.
	 * 
	 * @param c
	 *            The color to multiply.
	 * @return The new ColorRGBA. this*c
	 */
	public Color mult(Color c) {
		return new Color(c.r * r, c.g * g, c.b * b, c.a * a);
	}

	/**
	 * Multiplies each r/g/b/a of this color by the r/g/b/a of the given color
	 * and returns the result as a new ColorRGBA. Used as a way of combining
	 * colors and lights.
	 * 
	 * @param scalar
	 *            scalar to multiply with
	 * @return The new ColorRGBA. this*c
	 */
	public Color multLocal(float scalar) {
		this.r *= scalar;
		this.g *= scalar;
		this.b *= scalar;
		this.a *= scalar;
		return this;
	}

	/**
	 * Adds each r/g/b/a of this color by the r/g/b/a of the given color and
	 * returns the result as a new ColorRGBA.
	 * 
	 * @param c
	 *            The color to add.
	 * @return The new ColorRGBA. this+c
	 */
	public Color add(Color c) {
		return new Color(c.r + r, c.g + g, c.b + b, c.a + a);
	}

	/**
	 * Multiplies each r/g/b/a of this color by the r/g/b/a of the given color
	 * and returns the result as a new ColorRGBA. Used as a way of combining
	 * colors and lights.
	 * 
	 * @param c
	 *            The color to multiply.
	 * @return The new ColorRGBA. this*c
	 */
	public Color addLocal(Color c) {
		set(c.r + r, c.g + g, c.b + b, c.a + a);
		return this;
	}

	/**
	 * <code>toString</code> returns the string representation of this color.
	 * The format of the string is:<br>
	 * <Class Name>: [R=RR.RRRR, G=GG.GGGG, B=BB.BBBB, A=AA.AAAA]
	 * 
	 * @return the string representation of this color.
	 */
	@Override
	public String toString() {
		return "(" + r + ", " + g + ", " + b + ", " + a + ")";
	}

	/**
	 * <code>equals</code> returns true if this color is logically equivalent to
	 * a given color. That is, if the values of the two colors are the same.
	 * False is returned otherwise.
	 * 
	 * @param o
	 *            the object to compare againts.
	 * @return true if the colors are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Color)) {
			return false;
		}

		if (this == o) {
			return true;
		}

		Color comp = (Color) o;
		if (Float.compare(r, comp.r) != 0) {
			return false;
		}
		if (Float.compare(g, comp.g) != 0) {
			return false;
		}
		if (Float.compare(b, comp.b) != 0) {
			return false;
		}
		if (Float.compare(a, comp.a) != 0) {
			return false;
		}
		return true;
	}

	/**
	 * @inhericDoc
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 17 * hash + Float.floatToIntBits(this.r);
		hash = 17 * hash + Float.floatToIntBits(this.g);
		hash = 17 * hash + Float.floatToIntBits(this.b);
		hash = 17 * hash + Float.floatToIntBits(this.a);
		return hash;
	}

	/**
	 * Packs the 4 components of this color into a 32-bit int and returns it as
	 * a float.
	 * 
	 * @return the packed color as a 32-bit float
	 */
	public float toFloatBits() {
		int color = ((int) (255 * a) << 24) | ((int) (255 * b) << 16)
				| ((int) (255 * g) << 8) | ((int) (255 * r));
		return Float.intBitsToFloat(color & 0xfeffffff);
	}

	/**
	 * Packs the 4 components of this color into a 32-bit int.
	 * 
	 * @return the packed color as a 32-bit int.
	 */
	public int toIntBits() {
		int color = ((int) (255 * a) << 24) | ((int) (255 * b) << 16)
				| ((int) (255 * g) << 8) | ((int) (255 * r));
		return color;
	}

}
