package cs8803ca.project1;

import processing.core.*;
import processing.pdf.*; // to save screen shots as PDFs

public class Project1 extends PApplet
{
	/**
	 * Generated serial version UID. Do not modify.
	 */
	private static final long serialVersionUID = 8000097425606867575L;
	
	private static final Boolean isFullScreen = false;

	public static void main(String args[])
	{
		if(isFullScreen)
		{
			PApplet.main(new String[] { "--present", "cs8803ca.project1.Project1" });
		}
		else
		{
			PApplet.main(new String[] { "cs8803ca.project1.Project1" });
		}
		
	}

	// LecturesInGraphics: vector interpolation
	// Template for sketches
	// Author: Jarek ROSSIGNAC

	// **************************** global variables
	// ****************************
	pts P = new pts();
	float t = 1, f = 0;
	Boolean animate = true, linear = true, circular = true, beautiful = false, neville_manual = true, neville_generated = false;
	float len = 60; // length of arrows

	// **************************** initialization ****************************
	public void setup()
	{ // executed once at the beginning
//		size(600, 600); // window size
		frameRate(30); // render 30 frames per second
//		smooth(); // turn on antialiasing
		myFace = loadImage("data/pic.jpg"); // load image from file pic.jpg in
		// folder data *** replace that file
		// with your pic of your own face
		P.declare().resetOnCircle(3);
		P.loadPts("data/pts");
	}

	public void settings()
	{
		size(600, 600); // window size
		smooth(); // turn on antialiasing
	}

	// **************************** display current frame
	// ****************************
	public void draw()
	{ // executed at each frame
		background(white); // clear screen and paints white background
		if (snapPic)
			beginRecord(PDF, PicturesOutputPath + "/P"
					+ nf(pictureCounter++, 3) + ".pdf");

		P.G[1] = P(P.G[0], len, U(P.G[0], P.G[1]));
		P.G[3] = P(P.G[2], len, U(P.G[2], P.G[3]));
		P.G[5] = P(P.G[4], len, U(P.G[4], P.G[5]));
		pt A = P.G[0], B = P.G[1], C = P.G[2], D = P.G[3], E = P.G[4], F = P.G[5];
		vec AB = V(A, B), CD = V(C, D), EF = V(E, F);
		noStroke();
		
		if (animating)
		{
			t += 0.01;
			if (t >= 1)
			{
				t = 1;
				animating = false;
			}
		}

		if (linear)
		{
			float a = angle(AB, CD);
			fill(blue);
			scribeHeader("1-LINEAR", 1);
			for (float s = 0; s <= t; s += 0.1)
			{
				pt P = L(A, C, s);
				vec V = R(AB, s * a);
				noFill();
				pen(cyan, 3);
				drawObject(P, V);
			}
			pt P = L(A, C, t);
			vec V = R(AB, t * a);
			fill(blue);
			pen(blue, 3);
			show(P, 4);
			drawObject(P, V);
		}

		if (circular)
		{
			float a = angle(AB, CD);
			fill(brown);
			scribeHeader("2-CIRCULAR", 2);
			// for each s compute fixed point F and (P,V) for along pure
			// constant speed rotation around F from (A,AB) to (C,CD)
			pt mp = P(((A.x + C.x) / 2), ((A.y + C.y) / 2));
			// show(mp);
			float t1 = (d(A, C) / (2 * tan(a / 2)));
			vec MCr = R(V(mp, C));
			pt mcr = P(mp, MCr);
			pt Flocal = MoveByDistanceTowards(mp, t1, mcr);
			show(Flocal);
			vec FA = V(Flocal, A);
			pt P = P(A);
			// System.out.println("t= "+t);
			// System.out.println(" a = "+abs(a));
			for (float s = 0; s <= t; s += 0.1)
			{
				if (abs((s * a)) <= abs(a))
				{
					vec V = R(AB, s * a);
					P = R(A, (s * a), Flocal);
					// pt P = P(A,(s*a),FA);
					noFill();
					pen(sand, 3);
					drawObject(P, V);
					// System.out.println("s*a = "+(s*a)+" a = "+a+" P.x= "+
					// P.x+" P.y = "+P.y+" C.x= "+C.x+" t = "+t+" Angle V = "+angle(V));
				}
			}
			// for t compute fixed point F and (P,V) for along pure constant
			// speed rotation around F from (A,AB) to (C,CD)
			// System.out.println("t= "+t);
			P = R(A, (t * a), Flocal);
			vec V = R(AB, t * a);
			// System.out.println(" P.x= "+
			// P.x+" P.y = "+P.y+" Angle V = "+angle(V));
			fill(brown);
			pen(brown, 3);
			show(P, 4);
			drawObject(P, V);
		}

		if (beautiful)
		{
			float a = angle(AB, CD);
			fill(magenta);
			scribeHeader("3-BEAUTIFUL", 3);
			for (float s = 0; s <= t; s += 0.1)
			{
				// for each s compute (P,V) for beautiful motion from (A,AB) to
				// (C,CD)

				pt P = A; // replace this
				vec V = AB; // replace this

				noFill();
				pen(pink, 3);
				drawObject(P, V);
			}
			// for t compute (P,V) for beautiful motion from (A,AB) to (C,CD)

			pt P = A; // replace this
			vec V = AB; // replace this
			fill(magenta);
			pen(magenta, 3);
			show(P, 4);
			drawObject(P, V);
		}
		
		if (neville_manual)
		{
			float a = angle(AB, CD);
			fill(pink);
			scribeHeader("4-Neville Manually Selected Point", 4);
			pt X = A, P = A, Q = A;
			vec VX = AB, VP = AB, VQ = AB;
			for (float s = 0; s <= t; s += 0.1)
			{
				/*
				 *	N(a,P,c,Q,t) between P=N(a,A,e,E,t) and Q=N(e,E,c,C,t)
					N(a, N(a,A,e,E,t) ,c, N(e,E,c,C,t) ,t)
				 * */
				P = GL(0, A, 1f/2f, E, s);
				Q = GL(1f/2f, E, 1, C, s);
				X = GL(0, P, 1, Q, s);
				VP = slerp(AB, s, EF);
				VQ = slerp(EF, s, CD);
				VX = slerp(VP, s, VQ);

				noFill();
				pen(pink, 3);
				show(X, 4);
				drawObject(X, VX);
			}
			
			P = GL(0, A, 1f/2f, E, t);
			Q = GL(1f/2f, E, 1, C, t);
			X = GL(0, P, 1, Q, t);
			VP = slerp(AB, t, EF);
			VQ = slerp(EF, t, CD);
			VX = slerp(VP, t, VQ);
			
			fill(pink);
			pen(pink, 3);
			show(X, 4);
			drawObject(X, VX);
		}
		
		if (neville_generated)
		{
			float a = angle(AB, CD);
			fill(yellow);
			scribeHeader("5-Neville Generated Automatically", 3);
			for (float s = 0; s <= t; s += 0.1)
			{
				// for each s compute (P,V) for beautiful motion from (A,AB) to
				// (C,CD)

				pt P = A; // replace this
				vec V = AB; // replace this

				noFill();
				pen(pink, 3);
				drawObject(P, V);
			}
			// for t compute (P,V) for beautiful motion from (A,AB) to (C,CD)

			pt P = A; // replace this
			vec V = AB; // replace this
			fill(magenta);
			pen(magenta, 3);
			show(P, 4);
			drawObject(P, V);
		}

		pen(green, 3);
		fill(green);
		show(A, 4);
		arrow(A, B); // show the start and end arrows
		pen(red, 3);
		fill(red);
		show(C, 4);
		arrow(C, D);
		pen(pink, 3);
		fill(pink);
		show(E, 4);
		arrow(E, F);

		if (snapPic)
		{
			endRecord();
			snapPic = false;
		} // end saving a .pdf of the screen

		fill(black);
		displayHeader();
		if (scribeText && !filming)
			displayFooter(); // shows title, menu, and my face & name
		if (filming && (animating || change))
			saveFrame("FRAMES/F" + nf(frameCounter++, 4) + ".tif"); // saves a
		// movie
		// frame
		change = false; // to avoid capturing movie frames when nothing happens
	} // end of draw()

	// **************************** user actions ****************************
	public void keyPressed()
	{ // executed each time a key is pressed: sets the "keyPressed" and "key"
		// state variables,
		// till it is released or another key is pressed or released
		if (key == '?')
			scribeText = !scribeText; // toggle display of help text and authors
		// picture
		if (key == '!')
			snapPicture(); // make a picture of the canvas and saves as .jpg
		// image
		if (key == '`')
			snapPic = true; // to snap an image of the canvas and save as
		// zoomable a PDF
		if (key == '~')
		{
			filming = !filming;
		} // filming on/off capture frames into folder FRAMES
		if (key == 'a')
		{
			animating = true;
			f = 0;
			t = 0;
		}
		if (key == 's')
			P.savePts("data/pts");
		if (key == 'l')
			P.loadPts("data/pts");
		if (key == '1')
			linear = !linear;
		if (key == '2')
			circular = !circular;
		if (key == '3')
			beautiful = !beautiful;
		if (key == '4')
			neville_manual = !neville_manual;
		if (key == '5')
			neville_generated = !neville_generated;
		if (key == 'Q')
			exit(); // quit application
		change = true; // to make sure that we save a movie frame each time
		// something changes
	}

	public void mousePressed()
	{ // executed when the mouse is pressed
		P.pickClosest(Mouse()); // used to pick the closest vertex of C to the
		// mouse
		change = true;
	}

	public void mouseDragged()
	{
		if (!keyPressed || (key == 'a'))
			P.dragPicked(); // drag selected point with mouse
		if (keyPressed)
		{
			if (key == '.')
				f += 2. * (float) (mouseX - pmouseX) / width; // adjust current
			// frame
			if (key == 't')
				P.dragAll(); // move all vertices
			if (key == 'r')
				P.rotateAllAroundCentroid(Mouse(), Pmouse()); // turn all
			// vertices
			// around their
			// center of
			// mass
			if (key == 'z')
				P.scaleAllAroundCentroid(Mouse(), Pmouse()); // scale all
			// vertices with
			// respect to
			// their center
			// of mass
		}
		change = true;
	}

	// **************************** text for name, title and help
	// ****************************
	String title = "CA 2015 P1: Interpolation",
			name = "Student: Sumithra Sriram",
			menu = "?:(show/hide) help, a: animate, `:snap picture, ~:(start/stop) recording movie frames, Q:quit",
			guide = "drag:edit P&V, t/r/z:trans/rotate/zoom all, 1/2/3:toggle linear/circular/beautiful"; // help
	// info

	void drawObject(pt P, vec V)
	{
		beginShape();
		v(P(P(P, 1, V), 0.25f, R(V)));
		v(P(P(P, 1, V), -0.25f, R(V)));
		v(P(P(P, -1, V), -0.25f, R(V)));
		v(P(P(P, -1, V), 0.25f, R(V)));
		endShape(CLOSE);
	}

	float timeWarp(float f)
	{
		return sq(sin(f * PI / 2));
	}

	// *****************************************************************************
	// TITLE: GEOMETRY UTILITIES IN 2D
	// DESCRIPTION: Classes and functions for manipulating points, vectors,
	// edges, triangles, quads, frames, and circular arcs
	// AUTHOR: Prof Jarek Rossignac
	// DATE CREATED: September 2009
	// EDITS: Revised July 2011
	// *****************************************************************************
	// ************************************************************************
	// **** POINT CLASS
	// ************************************************************************
	class pt
	{
		float x = 0, y = 0;

		// CREATE
		pt()
		{
		}

		pt(float px, float py)
		{
			x = px;
			y = py;
		};

		// MODIFY
		pt setTo(float px, float py)
		{
			x = px;
			y = py;
			return this;
		};

		pt setTo(pt P)
		{
			x = P.x;
			y = P.y;
			return this;
		};

		pt setToMouse()
		{
			x = mouseX;
			y = mouseY;
			return this;
		};

		pt add(float u, float v)
		{
			x += u;
			y += v;
			return this;
		} // P.add(u,v): P+=<u,v>

		pt add(pt P)
		{
			x += P.x;
			y += P.y;
			return this;
		}; // incorrect notation, but useful for computing weighted averages

		pt add(float s, pt P)
		{
			x += s * P.x;
			y += s * P.y;
			return this;
		}; // adds s*P

		pt add(vec V)
		{
			x += V.x;
			y += V.y;
			return this;
		} // P.add(V): P+=V

		pt add(float s, vec V)
		{
			x += s * V.x;
			y += s * V.y;
			return this;
		} // P.add(s,V): P+=sV

		pt translateTowards(float s, pt P)
		{
			x += s * (P.x - x);
			y += s * (P.y - y);
			return this;
		}; // transalte by ratio s towards P

		pt scale(float u, float v)
		{
			x *= u;
			y *= v;
			return this;
		};

		pt scale(float s)
		{
			x *= s;
			y *= s;
			return this;
		} // P.scale(s): P*=s

		pt scale(float s, pt C)
		{
			x *= C.x + s * (x - C.x);
			y *= C.y + s * (y - C.y);
			return this;
		} // P.scale(s,C): scales wrt C: P=L(C,P,s);

		pt rotate(float a)
		{
			float dx = x, dy = y, c = cos(a), s = sin(a);
			x = c * dx + s * dy;
			y = -s * dx + c * dy;
			return this;
		}; // P.rotate(a): rotate P around origin by angle a in radians

		pt rotate(float a, pt G)
		{
			float dx = x - G.x, dy = y - G.y, c = cos(a), s = sin(a);
			x = G.x + c * dx + s * dy;
			y = G.y - s * dx + c * dy;
			return this;
		}; // P.rotate(a,G): rotate P around G by angle a in radians

		pt rotate(float s, float t, pt G)
		{
			float dx = x - G.x, dy = y - G.y;
			dx -= dy * t;
			dy += dx * s;
			dx -= dy * t;
			x = G.x + dx;
			y = G.y + dy;
			return this;
		}; // fast rotate s=sin(a); t=tan(a/2);

		pt moveWithMouse()
		{
			x += mouseX - pmouseX;
			y += mouseY - pmouseY;
			return this;
		};

		// DRAW , WRITE
		pt write()
		{
			print("(" + x + "," + y + ")");
			return this;
		}; // writes point coordinates in text window

		pt v()
		{
			vertex(x, y);
			return this;
		}; // used for drawing polygons between beginShape(); and endShape();

		pt show(float r)
		{
			ellipse(x, y, 2 * r, 2 * r);
			return this;
		}; // shows point as disk of radius r

		pt show()
		{
			show(3);
			return this;
		}; // shows point as small dot

		pt label(String s, float u, float v)
		{
			fill(black);
			text(s, x + u, y + v);
			noFill();
			return this;
		};

		pt label(String s, vec V)
		{
			fill(black);
			text(s, x + V.x, y + V.y);
			noFill();
			return this;
		};

		pt label(String s)
		{
			label(s, 5, 4);
			return this;
		};
	} // end of pt class

	// ************************************************************************
	// **** VECTOR CLASS
	// ************************************************************************
	class vec
	{
		float x = 0, y = 0;

		// CREATE
		vec()
		{
		};

		vec(float px, float py)
		{
			x = px;
			y = py;
		};

		// MODIFY
		vec setTo(float px, float py)
		{
			x = px;
			y = py;
			return this;
		};

		vec setTo(vec V)
		{
			x = V.x;
			y = V.y;
			return this;
		};

		vec zero()
		{
			x = 0;
			y = 0;
			return this;
		}

		vec scaleBy(float u, float v)
		{
			x *= u;
			y *= v;
			return this;
		};

		vec scaleBy(float f)
		{
			x *= f;
			y *= f;
			return this;
		};

		vec reverse()
		{
			x = -x;
			y = -y;
			return this;
		};

		vec divideBy(float f)
		{
			x /= f;
			y /= f;
			return this;
		};

		vec normalize()
		{
			float n = sqrt(sq(x) + sq(y));
			if (n > 0.000001)
			{
				x /= n;
				y /= n;
			}
			;
			return this;
		};

		vec add(float u, float v)
		{
			x += u;
			y += v;
			return this;
		};

		vec add(vec V)
		{
			x += V.x;
			y += V.y;
			return this;
		};

		vec add(float s, vec V)
		{
			x += s * V.x;
			y += s * V.y;
			return this;
		};

		vec rotateBy(float a)
		{
			float xx = x, yy = y;
			x = xx * cos(a) - yy * sin(a);
			y = xx * sin(a) + yy * cos(a);
			return this;
		};

		vec left()
		{
			float m = x;
			x = -y;
			y = m;
			return this;
		};

		// OUTPUT VEC
		@Override
		public vec clone()
		{
			return (new vec(x, y));
		};

		// OUTPUT TEST MEASURE
		float norm()
		{
			return (sqrt(sq(x) + sq(y)));
		}

		boolean isNull()
		{
			return ((abs(x) + abs(y) < 0.000001));
		}

		float angle()
		{
			return (atan2(y, x));
		}

		// DRAW, PRINT
		void write()
		{
			println("<" + x + "," + y + ">");
		};

		void showAt(pt P)
		{
			line(P.x, P.y, P.x + x, P.y + y);
		};

		void showArrowAt(pt P)
		{
			line(P.x, P.y, P.x + x, P.y + y);
			float n = min(this.norm() / 10.0f, height / 50.0f);
			pt Q = P(P, this);
			vec U = S(-n, U(this));
			vec W = S(.3f, R(U));
			beginShape();
			Q.add(U).add(W).v();
			Q.v();
			Q.add(U).add(M(W)).v();
			endShape(CLOSE);
		};

		void label(String s, pt P)
		{
			P(P).add(0.5f, this).add(3, R(U(this))).label(s);
		};
	} // end vec class

	// ************************************************************************
	// **** POINTS FUNCTIONS
	// ************************************************************************
	// create
	pt P()
	{
		return P(0, 0);
	}; // make point (0,0)

	pt P(float x, float y)
	{
		return new pt(x, y);
	}; // make point (x,y)

	pt P(pt P)
	{
		return P(P.x, P.y);
	}; // make copy of point A

	pt Mouse()
	{
		return P(mouseX, mouseY);
	}; // returns point at current mouse location

	pt Pmouse()
	{
		return P(pmouseX, pmouseY);
	}; // returns point at previous mouse location

	pt ScreenCenter()
	{
		return P(width / 2, height / 2);
	} // point in center of canvas

	// transform
	pt R(pt Q, float a)
	{
		float dx = Q.x, dy = Q.y, c = cos(a), s = sin(a);
		return new pt(c * dx + s * dy, -s * dx + c * dy);
	}; // Q rotated by angle a around the origin

	pt R(pt Q, float a, pt C)
	{
		float dx = Q.x - C.x, dy = Q.y - C.y, c = cos(a), s = sin(a);
		return P(C.x + c * dx - s * dy, C.y + s * dx + c * dy);
	}; // Q rotated by angle a around point P

	pt P(pt P, vec V)
	{
		return P(P.x + V.x, P.y + V.y);
	} // P+V (P transalted by vector V)

	pt P(pt P, float s, vec V)
	{
		return P(P, W(s, V));
	} // P+sV (P transalted by sV)

	pt MoveByDistanceTowards(pt P, float d, pt Q)
	{
		return P(P, d, U(V(P, Q)));
	}; // P+dU(PQ) (transLAted P by *distance* s towards Q)!!!

	// average
	pt P(pt A, pt B)
	{
		return P((float) (A.x + B.x) / 2.0f, (float) (A.y + B.y) / 2.0f);
	}; // (A+B)/2 (average)

	pt P(pt A, pt B, pt C)
	{
		return P((A.x + B.x + C.x) / 3.0f, (A.y + B.y + C.y) / 3.0f);
	}; // (A+B+C)/3 (average)

	pt P(pt A, pt B, pt C, pt D)
	{
		return P(P(A, B), P(C, D));
	}; // (A+B+C+D)/4 (average)

	// weighted average
	pt P(float a, pt A)
	{
		return P(a * A.x, a * A.y);
	} // aA

	pt P(float a, pt A, float b, pt B)
	{
		return P(a * A.x + b * B.x, a * A.y + b * B.y);
	} // aA+bB, (a+b=1)

	pt P(float a, pt A, float b, pt B, float c, pt C)
	{
		return P(a * A.x + b * B.x + c * C.x, a * A.y + b * B.y + c * C.y);
	} // aA+bB+cC

	pt P(float a, pt A, float b, pt B, float c, pt C, float d, pt D)
	{
		return P(a * A.x + b * B.x + c * C.x + d * D.x, a * A.y + b * B.y + c
				* C.y + d * D.y);
	} // aA+bB+cC+dD

	// LERP
	pt L(pt A, pt B, float t)
	{
		return P(A.x + t * (B.x - A.x), A.y + t * (B.y - A.y));
	}
	
	//Generalized LERP
	pt GL(float a, pt A, float b, pt B, float t)
	{
		//GL = (b-t) / (b-a) A + (t-a) / (b-a) B
		return P(((b-t) / (b-a) * A.x + (t-a) / (b-a) * B.x), ((b-t) / (b-a) * A.y + (t-a) / (b-a) * B.y));
	}

	// measure
	boolean isSame(pt A, pt B)
	{
		return (A.x == B.x) && (A.y == B.y);
	} // A==B

	boolean isSame(pt A, pt B, float e)
	{
		return ((abs(A.x - B.x) < e) && (abs(A.y - B.y) < e));
	} // ||A-B||<e

	float d(pt P, pt Q)
	{
		return sqrt(d2(P, Q));
	}; // ||AB|| (Distance)

	float d2(pt P, pt Q)
	{
		return sq(Q.x - P.x) + sq(Q.y - P.y);
	}; // AB*AB (Distance squared)

	// ************************************************************************
	// **** VECTOR FUNCTIONS
	// ************************************************************************
	// create
	vec V(vec V)
	{
		return new vec(V.x, V.y);
	}; // make copy of vector V

	vec V(pt P)
	{
		return new vec(P.x, P.y);
	}; // make vector from origin to P

	vec V(float x, float y)
	{
		return new vec(x, y);
	}; // make vector (x,y)

	vec V(pt P, pt Q)
	{
		return new vec(Q.x - P.x, Q.y - P.y);
	}; // PQ (make vector Q-P from P to Q

	vec U(vec V)
	{
		float n = n(V);
		if (n == 0)
			return new vec(0, 0);
		else
			return new vec(V.x / n, V.y / n);
	}; // V/||V|| (Unit vector : normalized version of V)

	vec U(pt P, pt Q)
	{
		return U(V(P, Q));
	}; // PQ/||PQ| (Unit vector : from P towards Q)

	vec MouseDrag()
	{
		return new vec(mouseX - pmouseX, mouseY - pmouseY);
	}; // vector representing recent mouse displacement

	// weighted sum
	vec W(float s, vec V)
	{
		return V(s * V.x, s * V.y);
	} // sV

	vec W(vec U, vec V)
	{
		return V(U.x + V.x, U.y + V.y);
	} // U+V

	vec W(vec U, float s, vec V)
	{
		return W(U, S(s, V));
	} // U+sV

	vec W(float u, vec U, float v, vec V)
	{
		return W(S(u, U), S(v, V));
	} // uU+vV ( Linear combination)

	// transformed
	vec R(vec V)
	{
		return new vec(-V.y, V.x);
	}; // V turned right 90 degrees (as seen on screen)

	vec R(vec V, float a)
	{
		float c = cos(a), s = sin(a);
		return (new vec(V.x * c - V.y * s, V.x * s + V.y * c));
	}; // V rotated by a radians

	vec S(float s, vec V)
	{
		return new vec(s * V.x, s * V.y);
	}; // sV

	vec Reflection(vec V, vec N)
	{
		return W(V, -2.0f * dot(V, N), N);
	}; // reflection

	vec M(vec V)
	{
		return V(-V.x, -V.y);
	} // -V

	// Interpolation
	vec L(vec U, vec V, float s)
	{
		return new vec(U.x + s * (V.x - U.x), U.y + s * (V.y - U.y));
	}; // (1-s)U+sV (Linear interpolation between vectors)

	vec S(vec U, vec V, float s)
	{
		float a = angle(U, V);
		vec W = R(U, s * a);
		float u = n(U), v = n(V);
		return W(pow(v / u, s), W);
	} // steady interpolation from U to V

	// measure
	float dot(vec U, vec V)
	{
		return U.x * V.x + U.y * V.y;
	} // dot(U,V): U*V (dot product U*V)

	float det(vec U, vec V)
	{
		return dot(R(U), V);
	} // det | U V | = scalar cross UxV

	float n(vec V)
	{
		return sqrt(dot(V, V));
	}; // n(V): ||V|| (norm: length of V)

	float n2(vec V)
	{
		return sq(V.x) + sq(V.y);
	}; // n2(V): V*V (norm squared)

	boolean parallel(vec U, vec V)
	{
		return dot(U, R(V)) == 0;
	};

	float angle(vec U, vec V)
	{
		return atan2(det(U, V), dot(U, V));
	}; // angle <U,V> (between -PI and PI)

	float angle(vec V)
	{
		return (atan2(V.y, V.x));
	}; // angle between <1,0> and V (between -PI and PI)

	float angle(pt A, pt B, pt C)
	{
		return angle(V(B, A), V(B, C));
	} // angle <BA,BC>

	float turnAngle(pt A, pt B, pt C)
	{
		return angle(V(A, B), V(B, C));
	} // angle <AB,BC> (positive when right turn as seen on screen)

	int toDeg(float a)
	{
		return (int) (a * 180 / PI);
	} // convert radians to degrees

	float toRad(float a)
	{
		return (a * PI / 180);
	} // convert degrees to radians

	float positive(float a)
	{
		if (a < 0)
			return a + TWO_PI;
		else
			return a;
	} // adds 2PI to make angle positive

	// SLERP
	vec slerp(vec U, float t, vec V)
	{
		float a = angle(U, V);
		float b = sin((1.0f - t) * a), c = sin(t * a), d = sin(a);
		return W(b / d, U, c / d, V);
	} // UNIT vectors ONLY!

	// ************************************************************************
	// **** DISPLAY
	// ************************************************************************
	// point / polygon
	void show(pt P, float r)
	{
		ellipse(P.x, P.y, 2 * r, 2 * r);
	}; // draws circle of center r around P

	void show(pt P)
	{
		ellipse(P.x, P.y, 6, 6);
	}; // draws small circle around point

	// edge / arrow
	void edge(pt P, pt Q)
	{
		line(P.x, P.y, Q.x, Q.y);
	}; // draws edge (P,Q)

	void arrow(pt P, pt Q)
	{
		arrow(P, V(P, Q));
	} // draws arrow from P to Q

	void show(pt P, vec V)
	{
		line(P.x, P.y, P.x + V.x, P.y + V.y);
	} // show V as line-segment from P

	void show(pt P, float s, vec V)
	{
		show(P, S(s, V));
	} // show sV as line-segment from P

	void arrow(pt P, float s, vec V)
	{
		arrow(P, S(s, V));
	} // show sV as arrow from P

	void arrow(pt P, vec V, String S)
	{
		arrow(P, V);
		P(P(P, 0.70f, V), 15, R(U(V))).label(S, V(-5, 4));
	} // show V as arrow from P and print string S on its side

	void arrow(pt P, vec V)
	{
		show(P, V);
		float n = n(V);
		if (n < 0.01)
			return;
		float s = max(min(0.2f, 20.0f / n), 6.0f / n); // show V as arrow from P
		pt Q = P(P, V);
		vec U = S(-s, V);
		vec W = R(S(.3f, U));
		beginShape();
		v(P(P(Q, U), W));
		v(Q);
		v(P(P(Q, U), -1, W));
		endShape(CLOSE);
	};

	// triangle, polygon
	void v(pt P)
	{
		vertex(P.x, P.y);
	}; // vertex for drawing polygons between beginShape() and endShape()

	void show(pt A, pt B, pt C)
	{
		beginShape();
		A.v();
		B.v();
		C.v();
		endShape(CLOSE);
	} // render triangle A, B, C

	void show(pt A, pt B, pt C, pt D)
	{
		beginShape();
		A.v();
		B.v();
		C.v();
		D.v();
		endShape(CLOSE);
	} // render quad A, B, C, D

	// text
	void label(pt P, String S)
	{
		text(S, P.x - 4, P.y + 6.5f);
	} // writes string S next to P on the screen ( for example
	// label(P[i],str(i));)

	void label(pt P, vec V, String S)
	{
		text(S, P.x - 3.5f + V.x, P.y + 7 + V.y);
	} // writes string S at P+V

	// *****************************************************************************
	// TITLE: Point sequence for polylines and polyloops
	// AUTHOR: Prof Jarek Rossignac
	// DATE CREATED: September 2012
	// EDITS: Last revised Sept 10, 2012
	// *****************************************************************************
	class pts
	{
		int nv = 0;
		int pv = 0; // picked vertex
		int maxnv = 40; // max number of vertices
		pt[] G = new pt[maxnv]; // geometry table (vertices)

		pts()
		{
		}

		pts declare()
		{
			for (int i = 0; i < maxnv; i++)
				G[i] = P();
			return this;
		} // init points

		pts empty()
		{
			nv = 0;
			return this;
		}

		pts addPt(pt P)
		{
			G[nv].setTo(P);
			pv = nv;
			nv++;
			return this;
		}

		pts addPt(float x, float y)
		{
			G[nv].x = x;
			G[nv].y = y;
			pv = nv;
			nv++;
			return this;
		}

		pts resetOnCircle(int k)
		{ // init the points to be on a circle
			pt C = ScreenCenter();
			for (int i = 0; i < k; i++)
				addPt(R(P(C, V(0, -width / 3f)), 2.0f * PI * i / k, C));
			return this;
		}

		pts makeGrid(int w)
		{ // make a 2D grid of w x w vertices
			for (int i = 0; i < w; i++)
				for (int j = 0; j < w; j++)
					addPt(P(.7f * height * j / (w - 1) + .1f * height, .7f
							* height * i / (w - 1) + .1f * height));
			return this;
		}

		pts deletePickedPt()
		{
			for (int i = pv; i < nv; i++)
				G[i].setTo(G[i + 1]);
			pv = max(0, pv - 1);
			nv--;
			return this;
		}

		pts setPt(pt P, int i)
		{
			G[i].setTo(P);
			return this;
		}

		pts IDs()
		{
			for (int v = 0; v < nv; v++)
			{
				fill(white);
				show(G[v], 13);
				fill(black);
				if (v < 10)
					label(G[v], str(v));
				else
					label(G[v], V(-5, 0), str(v));
			}
			noFill();
			return this;
		}

		pts showPicked()
		{
			show(G[pv], 13);
			return this;
		}

		pts draw(int c)
		{
			fill(c);
			for (int v = 0; v < nv; v++)
				show(G[v], 13);
			return this;
		}

		pts draw()
		{
			for (int v = 0; v < nv; v++)
				show(G[v], 13);
			return this;
		}

		pts drawCurve()
		{
			beginShape();
			for (int v = 0; v < nv; v++)
				G[v].v();
			endShape();
			return this;
		}

		void pickClosest(pt M)
		{
			pv = 0;
			for (int i = 1; i < nv; i++)
				if (d(M, G[i]) < d(M, G[pv]))
					pv = i;
		}

		pt Centroid()
		{
			pt C = P();
			for (int i = 0; i < nv; i++)
				C.add(G[i]);
			return P(1.0f / nv, C);
		}

		pts dragPicked()
		{
			G[pv].moveWithMouse();
			return this;
		} // moves selected point (index p) by amount mouse moved recently

		pts dragAll()
		{
			for (int i = 0; i < nv; i++)
				G[i].moveWithMouse();
			return this;
		} // moves selected point (index p) by amount mouse moved recently

		pts moveAll(vec V)
		{
			for (int i = 0; i < nv; i++)
				G[i].add(V);
			return this;
		};

		pts rotateAll(float a, pt C)
		{
			for (int i = 0; i < nv; i++)
				G[i].rotate(a, C);
			return this;
		}; // rotates points around pt G by angle a

		pts rotateAllAroundCentroid(float a)
		{
			rotateAll(a, Centroid());
			return this;
		}; // rotates points around their center of mass by angle a

		pts rotateAll(pt G, pt P, pt Q)
		{
			rotateAll(angle(V(G, P), V(G, Q)), Centroid());
			return this;
		}; // rotates points around G by angle <GP,GQ>

		pts rotateAllAroundCentroid(pt P, pt Q)
		{
			rotateAll(Centroid(), P, Q);
			return this;
		}; // rotates points around their center of mass G by angle <GP,GQ>

		pts scaleAll(float s, pt C)
		{
			for (int i = 0; i < nv; i++)
				G[i].translateTowards(s, C);
			return this;
		};

		pts scaleAllAroundCentroid(float s)
		{
			scaleAll(s, Centroid());
			return this;
		};

		pts scaleAllAroundCentroid(pt M, pt P)
		{
			pt C = Centroid();
			float m = d(C, M), p = d(C, P);
			scaleAll((p - m) / p, C);
			return this;
		};

		pts fitToCanvas()
		{ // translates and scales mesh to fit canvas
			float sx = 100000;
			float sy = 10000;
			float bx = 0.0f;
			float by = 0.0f;
			for (int i = 0; i < nv; i++)
			{
				if (G[i].x > bx)
				{
					bx = G[i].x;
				}
				;
				if (G[i].x < sx)
				{
					sx = G[i].x;
				}
				;
				if (G[i].y > by)
				{
					by = G[i].y;
				}
				;
				if (G[i].y < sy)
				{
					sy = G[i].y;
				}
				;
			}
			for (int i = 0; i < nv; i++)
			{
				G[i].x = 0.93f * (G[i].x - sx) * (width) / (bx - sx) + 23;
				G[i].y = 0.90f * (G[i].y - sy) * (height - 100) / (by - sy)
						+ 100;
			}
			return this;
		}

		// void savePts() {
		// String savePath =
		// selectOutput("Select or specify file name where the points will be saved");
		// // Opens file chooser
		// if (savePath == null) {println("No output file was selected...");
		// return;}
		// else println("writing to "+savePath);
		// savePts(savePath);
		// }

		void savePts(String fn)
		{
			String[] inppts = new String[nv + 1];
			int s = 0;
			inppts[s++] = str(nv);
			for (int i = 0; i < nv; i++)
			{
				inppts[s++] = str(G[i].x) + "," + str(G[i].y);
			}
			saveStrings(fn, inppts);
		};

		// void loadPts() {
		// String loadPath = selectInput("Select file to load"); // Opens file
		// chooser
		// if (loadPath == null) {println("No input file was selected...");
		// return;}
		// else println("reading from "+loadPath);
		// loadPts(loadPath);
		// }

		void loadPts(String fn)
		{
			println("loading: " + fn);
			String[] ss = loadStrings(fn);
			String subpts;
			int s = 0;
			int comma, comma1, comma2;
			float x, y;
			int a, b, c;
			nv = (int) Integer.valueOf(ss[s++]);
			print("nv=" + nv);
			for (int k = 0; k < nv; k++)
			{
				int i = k + s;
				comma = ss[i].indexOf(',');
				x = (float) Float.valueOf(ss[i].substring(0, comma));
				y = (float) Float.valueOf(ss[i].substring(comma + 1,
						ss[i].length()));
				G[k].setTo(x, y);
			}
			;
			pv = 0;
		};

	} // end class pts

	// LecturesInGraphics: utilities
	// Author: Jarek ROSSIGNAC, last edited on July 21, 2015
	PImage myFace; // picture of author's face, should be: data/pic.jpg in
	// sketch folder

	// ************************************************************************
	// COLORS
	int black = color(0,0,0),
			white = color(255,255,255),
			red = color(255,0,0), green = color(0,255,0), blue = color(0,0,255),
			yellow = color(255,255,0), cyan = color(0,255,255), magenta = color(255,0,255),
			grey = color(127,127,127), brown = color(175,100,0), sand = color(252,186,105),
			pink = color(255,142,231);

	// ************************************************************************
	// GRAPHICS
	void pen(int c, float w)
	{
		stroke(c);
		strokeWeight(w);
	}

	void showDisk(float x, float y, float r)
	{
		ellipse(x, y, r * 2, r * 2);
	}

	// ************************************************************************
	// SAVING INDIVIDUAL IMAGES OF CANVAS
	boolean snapPic = false;
	String PicturesOutputPath = "data/PDFimages";
	int pictureCounter = 0;

	void snapPicture()
	{
		saveFrame("PICTURES/P" + nf(pictureCounter++, 3) + ".jpg");
	}

	// ************************ SAVING IMAGES for a MOVIE
	boolean filming = false; // when true frames are captured in FRAMES for a
	// movie
	int frameCounter = 0; // count of frames captured (used for naming the image
	// files)
	boolean change = false; // true when the user has presed a key or moved the
	// mouse
	boolean animating = false; // must be set by application during animations
	// to force frame capture
	/*
	 * To make a movie : Press '~' to start filming, act the movie or start an
	 * animation, press '~' to pause/stop (you can restart to add frames) Then,
	 * from within your Processing sketch, from the processing menu, select
	 * Tools > Movie Maker. Click on Choose… Navigate to your Sketch Folder.
	 * Select, but do not open, the FRAMES folder. Press Create Movie, Select
	 * the parameters you want.
	 * 
	 * May not work for a large canvas!
	 */

	// ************************************************************************
	// TEXT
	Boolean scribeText = true; // toggle for displaying of help text

	void scribe(String S, float x, float y)
	{
		fill(0);
		text(S, x, y);
		noFill();
	} // writes on screen at (x,y) with current fill color

	void scribeHeader(String S, int i)
	{
		text(S, 10, 20 + i * 20);
		noFill();
	} // writes black at line i

	void scribeHeaderRight(String S)
	{
		fill(0);
		text(S, width - 7.5f * S.length(), 20);
		noFill();
	} // writes black on screen top, right-aligned

	void scribeFooter(String S, int i)
	{
		fill(0);
		text(S, 10, height - 10 - i * 20);
		noFill();
	} // writes black on screen at line i from bottom

	void scribeAtMouse(String S)
	{
		fill(0);
		text(S, mouseX, mouseY);
		noFill();
	} // writes on screen near mouse

	void scribeMouseCoordinates()
	{
		fill(black);
		text("(" + mouseX + "," + mouseY + ")", mouseX + 7, mouseY + 25);
		noFill();
	}

	void displayHeader()
	{ // Displays title and authors face on screen
		scribeHeader(title, 0);
		scribeHeaderRight(name);
		image(myFace, width - myFace.width / 2, 25, myFace.width / 2,
				myFace.height / 2);
	}

	void displayFooter()
	{ // Displays help text at the bottom
		scribeFooter(guide, 1);
		scribeFooter(menu, 0);
	}

	// ************************************************************************
	// **** SPIRAL
	// ************************************************************************
	pt PtOnSpiral(pt A, pt B, pt C, float t)
	{
		float a = spiralAngle(A, B, B, C);
		float s = spiralScale(A, B, B, C);
		pt G = spiralCenter(a, s, A, B);
		return L(G, R(B, t * a, G), pow(s, t));
	}

	pt spiralPt(pt A, pt G, float s, float a)
	{
		return L(G, R(A, a, G), s);
	}

	pt spiralPt(pt A, pt G, float s, float a, float t)
	{
		return L(G, R(A, t * a, G), pow(s, t));
	}

	pt spiralCenter(pt A, pt B, pt C, pt D)
	{ // computes center of spiral that takes A to C and B to D
		float a = spiralAngle(A, B, C, D);
		float z = spiralScale(A, B, C, D);
		return spiralCenter(a, z, A, C);
	}

	float spiralAngle(pt A, pt B, pt C, pt D)
	{
		return angle(V(A, B), V(C, D));
	}

	float spiralScale(pt A, pt B, pt C, pt D)
	{
		return d(C, D) / d(A, B);
	}

	pt spiralCenter(float a, float z, pt A, pt C)
	{
		float c = cos(a), s = sin(a);
		float D = sq(c * z - 1) + sq(s * z);
		float ex = c * z * A.x - C.x - s * z * A.y;
		float ey = c * z * A.y - C.y + s * z * A.x;
		float x = (ex * (c * z - 1) + ey * s * z) / D;
		float y = (ey * (c * z - 1) - ex * s * z) / D;
		return P(x, y);
	}
}
