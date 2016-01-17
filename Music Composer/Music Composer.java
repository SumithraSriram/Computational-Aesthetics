package ca.project5;

import processing.core.*;
import org.jfugue.player.*;
import org.jfugue.pattern.*;
import org.jfugue.rhythm.*;

public class GUI extends PApplet
{
private static final long serialVersionUID = 8000097425606867575L;
	
	private static final Boolean isFullScreen = true;

	public static void main(String args[])
	{
		if(isFullScreen)
		{
			PApplet.main(new String[] { "--present", "ca.project5.GUI" });
		}
		else
		{
			PApplet.main(new String[] { "ca.project5.GUI" });
		}
		
	}


	// LecturesInGraphics: vector interpolation
	// Template for sketches
	// Author: Jarek ROSSIGNAC

	// **************************** global variables
	// ****************************
	String title ="CA 20015 P5: Music GUI", 
		       name ="Student: Sumithra Sriram",
		       menu="",
		       guide=""; // help info
		pts P = new pts();
		float f=0;
		boolean play = false;

		public void setup()
		{
		    frameRate(30);
		    P.declare().resetOnCircle(3);
		    P.loadPts("data/pts");
		}
		public void settings()
		{
			if(isFullScreen)
			{
				size(displayWidth, displayHeight);
			}
			else
			{
				size(600, 600); // window size
			}
			smooth(); // turn on antialiasing
		}
		
		public void draw()
		{    
		  background(white);
		  smooth(); 
		  strokeWeight(1);
		  fill(black);
		  pen(black, 1);
		  for(int i=0;i<=240;i+=20)
		  {
		   noFill();
		   ellipse(300,200,50+i,50+i);
		   ellipse(700,200,50+i,50+i);
		   ellipse(300,550,50+i,50+i);
		   ellipse(700,550,50+i,50+i);
		  }
		  pt A1 = P(300,200);
		  pt B1 = P(300, 0);
		  B1 = MoveByDistanceTowards(A1, 290/2, B1);
		  vec V11 = V(A1,B1);
		  show(A1,V11);
		  vec V12 = R(V11, 2*PI/3);
		  show(A1,V12);
		  vec V13 = R(V12, 2*PI/3);
		  show(A1,V13);
		  
		  pt A2 = P(700,200);
		  pt B2 = P(700, 0);
		  B2 = MoveByDistanceTowards(A2, 290/2, B2);
		  vec V21 = V(A2,B2);
		  show(A2,V21);
		  vec V22 = R(V21, 2*PI/4);
		  show(A2,V22);
		  vec V23 = R(V22, 2*PI/4);
		  show(A2,V23);
		  vec V24 = R(V23, 2*PI/4);
		  show(A2,V24);
		  
		  pt A3 = P(300,550);
		  pt B3 = P(300, 0);
		  B3 = MoveByDistanceTowards(A3, 290/2, B3);
		  vec V31 = V(A3,B3);
		  show(A3,V31);
		  vec V32 = R(V31, 2*PI/4);
		  show(A3,V32);
		  vec V33 = R(V32, 2*PI/4);
		  show(A3,V33);
		  vec V34 = R(V33, 2*PI/4);
		  show(A3,V34);
		  
		  pt A4 = P(700,550);
		  pt B4 = P(700, 0);
		  B4 = MoveByDistanceTowards(A4, 290/2, B4);
		  vec V41 = V(A4,B4);
		  show(A4,V41);
		  vec V42 = R(V41, 2*PI/5);
		  show(A4,V42);
		  vec V43 = R(V42, 2*PI/5);
		  show(A4,V43);
		  vec V44 = R(V43, 2*PI/5);
		  show(A4,V44);
		  vec V45 = R(V44, 2*PI/5);
		  show(A4,V45);  

		  P.draw();    
		  if(play)
		  {
			  int dist[] = new int [P.nv];
			  for(int i=0;i<=2;i++)
			  {
				  dist[i] = (int) d(A1, P.G[i]);				  
			  }
			  for(int i=3;i<=6;i++)
			  {
				  dist[i] = (int) d(A2, P.G[i]);				  
			  }
			  for(int i=7;i<=10;i++)
			  {
				  dist[i] = (int) d(A3, P.G[i]);				  
			  }
			  for(int i=11;i<=15;i++)
			  {
				  dist[i] = (int) d(A4, P.G[i]);				  
			  }
			  for(int i=0;i<=15;i++)
			  {
				  if(dist[i] <=55)
					  P.note[i] = "C";
				  else if (dist[i] <=75)
					  P.note[i] = "C#";
				  else if (dist[i] <=95)
					  P.note[i] = "D";
				  else if (dist[i] <=115)
					  P.note[i] = "D#";
				  else if (dist[i] <=135)
					  P.note[i] = "E";
				  else if (dist[i] <=155)
					  P.note[i] = "F";
				  else if (dist[i] <=175)
					  P.note[i] = "F#";
				  else if (dist[i] <=195)
					  P.note[i] = "G";
				  else if (dist[i] <=215)
					  P.note[i] = "G#";
				  else if (dist[i] <=235)
					  P.note[i] = "A#";
				  else if (dist[i] <=255)
					  P.note[i] = "A#";
				  else 
					  P.note[i] = "B";
			  }

			  
			String pat1="", pat2="", pat3="", pat4="", pp1="", pp2="", pp3="", pp4="", pper1="", pper2="", pper3="",
					pper4="", pattern1="", pattern2="", pattern3="", pattern4="";
			
			//For the First 3-beat pattern
			for(int i=0;i<=2;i++)
			{
				if(P.c[i] == green)
					pat1 = pat1+" I[Guitar] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == red)
					pat1 = pat1+" I[Flute] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == yellow)
					pat1 = pat1+" I[Cello] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == pink)
					pat1 = pat1+" I[Piano] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == magenta)
					pat1 = pat1+" I[Piano] "+P.note[i]+"maj"+P.octave[i]+" ";
				else if(P.c[i] == sand)
					pat1 = pat1+" I[Piano] "+P.note[i]+"min"+P.octave[i]+" ";
				else
					pat1 = pat1+" Rq ";
				if(P.c[i] == cyan)
				{
					if(P.r[i] == 10)
						pp1 = pp1+" [PEDAL_HI_HAT]q ";
					else if(P.r[i] < 10)
						pp1 = pp1+" [BASS_DRUM]q ";
					else
						pp1 = pp1+" [CRASH_CYMBAL_1]q ";
				}					
				else
					pp1 = pp1+" Rq ";
			}
						
			for(int i=0;i<20;i++)
			{
				pattern1 = pattern1 + pat1;
				pper1 = pper1 + pp1;
			}
			
			Pattern p1 = new Pattern("V0 "+pattern1+" V9 "+pper1);			
			
			// For the second 4-beat pattern
			for(int i=3;i<=6;i++)
			{
				if(P.c[i] == green)
					pat2 = pat2+" I[Guitar] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == red)
					pat2 = pat2+" I[Flute] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == yellow)
					pat2 = pat2+" I[Cello] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == pink)
					pat2 = pat2+" I[Piano] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == magenta)
					pat2 = pat2+" I[Piano] "+P.note[i]+"maj"+P.octave[i]+" ";
				else if(P.c[i] == sand)
					pat2 = pat2+" I[Piano] "+P.note[i]+"min"+P.octave[i]+" ";
				else
					pat2 = pat2+" Rq ";
				if(P.c[i] == cyan)
				{
					if(P.r[i] == 10)
						pp2 = pp2+" [PEDAL_HI_HAT]q ";
					else if(P.r[i] < 10)
						pp2 = pp2+" [BASS_DRUM]q ";
					else
						pp2 = pp2+" [CRASH_CYMBAL_1]q ";
				}					
				else
					pp2 = pp2+" Rq ";
			}			
			
			for(int i=0;i<15;i++)
			{
				pattern2 = pattern2 + pat2;
				pper2 = pper2 + pp2;
			}
			Pattern p2 = new Pattern("V1 "+pattern2+" V9 "+pper2);
			
			// For the third 4-beat pattern
			for(int i=7;i<=10;i++)
			{
				if(P.c[i] == green)
					pat3 = pat3+" I[Guitar] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == red)
					pat3 = pat3+" I[Flute] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == yellow)
					pat3 = pat3+" I[Cello] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == pink)
					pat3 = pat3+" I[Piano] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == magenta)
					pat3 = pat3+" I[Piano] "+P.note[i]+"maj"+P.octave[i]+" ";
				else if(P.c[i] == sand)
					pat3 = pat3+" I[Piano] "+P.note[i]+"min"+P.octave[i]+" ";
				else
					pat3 = pat3+" Rq ";
				if(P.c[i] == cyan)
				{
					if(P.r[i] == 10)
						pp3 = pp3+" [PEDAL_HI_HAT]q ";
					else if(P.r[i] < 10)
						pp3 = pp3+" [BASS_DRUM]q ";
					else
						pp3 = pp3+" [CRASH_CYMBAL_1]q ";
				}					
				else
					pp3 = pp3+" Rq ";
			}						
			
			for(int i=0;i<15;i++)
			{
				pattern3 = pattern3 + pat3;
				pper3 = pper3 + pp3;
			}
			Pattern p3 = new Pattern("V2 "+pattern3+" V9 "+pper3);	
			
			// For the fourth 5-beat pattern
			for(int i=11;i<=15;i++)
			{
				if(P.c[i] == green)
					pat4 = pat4+" I[Guitar ] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == red)
					pat4 = pat4+" I[Flute] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == yellow)
					pat4 = pat4+" I[Cello] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == pink)
					pat4 = pat4+" I[Piano] "+P.note[i]+P.octave[i]+" ";
				else if(P.c[i] == magenta)
					pat4 = pat4+" I[Piano] "+P.note[i]+"maj"+P.octave[i]+" ";
				else if(P.c[i] == sand)
					pat4 = pat4+" I[Piano] "+P.note[i]+"min"+P.octave[i]+" ";
				else
					pat4 = pat4+" Rq ";
				if(P.c[i] == cyan)
				{
					if(P.r[i] == 10)
						pp4 = pp4+" [PEDAL_HI_HAT]q ";
					else if(P.r[i] < 10)
						pp4 = pp4+" [BASS_DRUM]q ";
					else
						pp4 = pp4+" [CRASH_CYMBAL_1]q ";
				}					
				else
					pp4 = pp4+" Rq ";
			}			
			
			for(int i=0;i<12;i++)
			{
				pattern4 = pattern4 + pat4;
				pper4 = pper4 + pp4;
			}
			Pattern p4 = new Pattern("V3 "+pattern4+" V9 "+pper4);	


	        //PEDAL_HI_HAT, BASS_DRUM , CRASH_CYMBAL_1
	        Player player1 = new Player();
	        
	        //Pattern p = new Pattern("V9 A [PEDAL_HI_HAT]q");
			//player1.play(p);
			
	        player1.play(p1,p2,p3,p4);
	        
		  }
		  change = false;
		}

		public void keyPressed() { 
			
		    if(key=='g') {P.c[P.pv]=green;} //Guitar
		    if(key=='r') {P.c[P.pv]=red;} //Flute
		    if(key=='b') {P.c[P.pv]=black;} //Back to inactive
		    if(key=='y') {P.c[P.pv]=yellow;} //Cello
		    if(key=='p') {P.c[P.pv]=pink;} //Piano
		    if(key=='c') {P.c[P.pv]=cyan;} //Drums
		    if(key=='m') {P.c[P.pv]=magenta;} //Major Chord
		    if(key=='s') {P.c[P.pv]=sand;} //Minor Chord
		    if(key==' ') {play = !play;}
		    if(key=='+') {P.r[P.pv] += 2; ++P.octave[P.pv];} //Increase by 1 octave
		    if(key=='-') {P.r[P.pv] -= 2; --P.octave[P.pv];} //Decrease by 1 octave
		    
		
		  change=true; // to make sure that we save a movie frame each time something changes
		  }

		public void mousePressed() {  // executed when the mouse is pressed
		 P.pickClosest(Mouse()); // used to pick the closest vertex of C to the mouse
		 change=true;
		 }

		public void mouseDragged() {
		 P.dragPicked();   // drag selected point with mouse
		//Make the boundaries solid
		 change=true;
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
		}; // translate by ratio s towards P

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
			show(5);
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
		int[] c = new int[maxnv]; //to store the color of the point
		int[] r = new int[maxnv]; //to store the radius of the point
		String[] note = new String[maxnv];
		int[] octave = new int[maxnv];

		pts()
		{
		}

		pts declare()
		{
			for (int i = 0; i < maxnv; i++)
			{
				G[i] = P();
				c[i] = black;
				r[i] = 10;
				note[i] = "C";
				octave[i] = 5;
			}
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
			c[nv]=black;
			r[nv]=10;
			note[nv] = "C";
			octave[nv] = 5;
			pv = nv;
			nv++;
			return this;
		}

		pts addPt(float x, float y)
		{
			G[nv].x = x;
			G[nv].y = y;
			c[nv]=black;
			r[nv]=10;
			note[nv] = "C";
			octave[nv] = 5;
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
			{
				G[i].setTo(G[i + 1]);
				c[i] = c[i+1];
				r[i] = r[i+1];
				note[i] = note[i+1];
				octave[i] = octave[i+1];
			}
			pv = max(0, pv - 1);
			nv--;
			return this;
		}

		pts setPt(pt P, int i)
		{
			G[i].setTo(P);
			c[i] = black;
			r[i] = 10;
			note[i] = "C";
			octave[i] = 5;
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
			show(G[pv], r[pv]);
			return this;
		}

		pts draw(int c)
		{
			fill(c);
			for (int v = 0; v < nv; v++)
				show(G[v], r[v]);
			return this;
		}

		pts draw()
		{
			for (int v = 0; v < nv; v++)
			{
				fill(c[v]);
				show(G[v], r[v]);
			}
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
			pink = color(255,142,231), light_blue = color(0, 0, 127), violet = color(140, 110, 148),
			dark_green = color(124, 148, 110), dark_red = color(145, 65, 65);

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

}
