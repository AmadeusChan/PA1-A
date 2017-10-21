//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short COMPLEX=261;
public final static short CLASS=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short SUPER=266;
public final static short WHILE=267;
public final static short FOR=268;
public final static short IF=269;
public final static short ELSE=270;
public final static short RETURN=271;
public final static short BREAK=272;
public final static short NEW=273;
public final static short CASE=274;
public final static short DEFAULT=275;
public final static short PRINT=276;
public final static short READ_INTEGER=277;
public final static short READ_LINE=278;
public final static short PRINTCOMP=279;
public final static short LITERAL=280;
public final static short IDENTIFIER=281;
public final static short AND=282;
public final static short OR=283;
public final static short STATIC=284;
public final static short INSTANCEOF=285;
public final static short LESS_EQUAL=286;
public final static short GREATER_EQUAL=287;
public final static short EQUAL=288;
public final static short NOT_EQUAL=289;
public final static short DCOPY=290;
public final static short SCOPY=291;
public final static short UMINUS=292;
public final static short EMPTY=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   27,   28,   28,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   29,   29,   26,   26,   30,   30,   16,   17,   20,
   15,   31,   31,   18,   18,   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    3,    1,    0,
    2,    0,    2,    4,    5,    4,    5,    0,    4,    4,
    1,    8,    1,    1,    1,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    2,
    2,    2,    2,    2,    3,    3,    1,    4,    5,    6,
    5,    1,    1,    1,    0,    3,    1,    5,    9,    1,
    6,    2,    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   83,   77,   51,    0,
    0,    0,    0,   90,    0,    0,    0,    0,    0,    0,
   82,    0,    0,    0,    0,    0,    0,   25,    0,    0,
    0,   28,   36,   26,    0,   30,   31,   32,    0,    0,
    0,    0,    0,    0,    0,    0,   55,    0,    0,    0,
   53,    0,   54,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   72,   73,   74,
   29,   33,   34,   35,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   75,   76,    0,    0,    0,    0,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   78,    0,    0,
    0,   96,   97,    0,   49,   50,    0,   44,    0,    0,
   88,    0,    0,   79,   48,    0,    0,   81,   45,    0,
    0,   91,    0,   80,    0,   92,    0,    0,    0,    0,
    0,   52,    0,   89,    0,    0,   46,   47,
};
final static short yydgoto[] = {                          2,
    3,    4,   72,   21,   34,    8,   11,   23,   35,   36,
   73,   46,   74,   75,   76,   77,   78,   79,   80,   81,
   82,   91,   84,   93,   86,  179,  198,  193,   87,  140,
  192,
};
final static short yysindex[] = {                      -258,
 -273,    0, -258,    0, -237,    0, -245,  -82,    0,    0,
  270,    0,    0,    0,    0,    0, -241,  -57,    0,    0,
  -15,  -74,    0,    0,  -73,    0,    5,  -43,   34,  -57,
    0,  -57,    0,  -72,   35,   31,   40,    0,  -38,  -57,
  -38,    0,    0,    0,    0,    2,    0,    0,    0,   46,
   47,   48,  100,    0, -108,   55,   56,   60,   62,   71,
    0,   76,   78,   80,  100,  100,   58,    0,  100,  100,
  100,    0,    0,    0,   45,    0,    0,    0,   72,   73,
   75,   82,   67,  855,    0, -160,    0,  100,  100,  100,
    0,  855,    0,   90,   51,  100,  100,   96,   97,  100,
  100,  100,  100,  -24,  -24, -142,  452,    0,    0,    0,
    0,    0,    0,    0,    0,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,  100,    0,
  100,  106,  478,   88,  504,  107,   79,  515,  855,   16,
    0,    0,   20,  536,  562,  588,  114,    0,  855,  908,
  887,    6,    6,  -32,  -32,  -13,  -13,  -24,  -24,  -24,
    6,    6,  768,  100,   37,  100,   37,    0,  792,   33,
  100,    0,    0, -123,    0,    0,  100,    0,  118,  119,
    0,  709,  -96,    0,    0,  855,  121,    0,    0,  100,
   37,    0, -217,    0,  134,    0,  122,   52,  123,   37,
  100,    0,  100,    0,  820,  844,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  176,    0,   59,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  120,    0,    0,  146,
    0,  146,    0,    0,    0,  148,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,    0,  -50,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -91,  -91,  -91,    0,  -91,  -91,
  -91,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  876,    0,  151,    0,    0,  -91,  -58,  -91,
    0,  132,    0,    0,    0,  -91,  -91,    0,    0,  -91,
  -91,  -91,  -91,  355,  382,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -91,  -91,  -91,  -91,  -91,
  -91,  -91,  -91,  -91,  -91,  -91,  -91,  -91,  -91,    0,
  -91,  124,    0,    0,    0,    0,  -91,    0,   65,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -20,   64,
   24,  740,  866,   85,  335,  917,  928,  408,  417,  443,
  941,  945,    0,  -25,  -58,  -91,  -58,    0,    0,    0,
  -91,    0,    0,    0,    0,    0,  -91,    0,    0,  154,
    0,    0,  -33,    0,    0,   69,    0,    0,    0,   -9,
  -58,    0,    0,    0,    0,    0,    0,    0,    0,  -58,
  -91,    0,  -91,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  196,  195,   39,   44,    0,    0,    0,  180,    0,
  -16,    0, -111,  -83,    0,    0,    0,    0,    0,    0,
    0,  316, 1172,  420,    0,    0,    0,    0,   21,  -80,
    0,
};
final static int YYTABLESIZE=1375;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         93,
   40,   93,   93,    1,  127,  134,   93,    5,   95,  125,
  123,   93,  124,  130,  126,   85,   28,   28,   28,  143,
   38,  130,   43,  127,   45,   93,    7,  129,  125,  128,
   93,   40,  130,  126,   66,    9,   71,   70,   38,   24,
   10,   67,  127,   26,   30,   47,   65,  125,  123,   31,
  124,  130,  126,  181,   22,  183,  172,  197,  131,  171,
  173,   25,   61,  171,   68,   69,  131,   68,   33,   66,
   33,   71,   70,   32,   40,   39,   67,  131,   44,  196,
   41,   65,   68,  180,   42,   88,   89,   90,  204,   93,
   66,   93,   71,   70,   96,   97,  131,   67,   95,   98,
   69,   99,   65,  111,   67,   87,  195,   67,   87,   86,
  100,   66,   86,   71,   70,  101,   68,  102,   67,  103,
  132,   69,   67,   65,   42,   61,   68,  116,   61,  136,
  112,  113,   66,  114,   71,   70,  141,  142,  147,   67,
  115,  137,   69,   61,   65,  164,  166,  168,   12,   13,
   14,   15,   16,   17,  177,  185,   67,  187,  189,   42,
   43,  194,  171,   69,   43,   43,   43,   43,   43,   43,
   43,   31,   94,  191,  200,    1,  202,   61,    5,  201,
  203,   15,   43,   43,   43,   43,   20,   54,   19,   42,
   94,   39,   54,   54,   84,   54,   54,   54,    6,   12,
   13,   14,   15,   16,   17,   20,   27,   29,   38,   39,
   54,   37,   54,  199,   43,    0,   43,    0,    0,    0,
    0,    0,   42,   93,   93,   93,   93,   93,   93,   93,
   42,   93,   93,   93,   93,   93,    0,   93,   93,   93,
   93,   54,   93,   93,   93,   93,   93,   93,    0,    0,
    0,   93,    0,  119,  120,   42,   93,   93,   12,   13,
   14,   15,   16,   17,   47,    0,   48,   49,   50,   51,
   52,   42,   53,   54,   55,   56,    0,   57,   58,   59,
   60,   61,    0,    0,    0,    0,   62,    0,    0,    0,
    0,   63,   64,   12,   13,   14,   15,   16,   17,   47,
    0,   48,   49,   50,   51,   52,   68,   53,   54,   55,
   56,    0,   57,   58,   59,   60,   61,    0,    0,  106,
   47,   62,   48,   49,    0,    0,   63,   64,    0,    0,
   55,   56,    0,    0,   58,   59,    0,   61,    0,    0,
    0,   47,   62,   48,   49,   67,   67,   63,   64,    0,
    0,   55,   56,    0,    0,   58,   59,    0,   61,    0,
    0,   83,   47,   62,   48,   49,   61,   61,   63,   64,
    0,    0,   55,   56,    0,   62,   58,   59,   62,   61,
    0,    0,    0,    0,   62,    0,    0,    0,    0,   63,
   64,   70,    0,   62,   19,   70,   70,   70,   70,   70,
    0,   70,    0,    0,   83,   43,   43,    0,    0,   43,
   43,   43,   43,   70,   70,    0,   70,    0,   71,    0,
    0,    0,   71,   71,   71,   71,   71,   62,   71,    0,
    0,    0,   54,   54,    0,    0,   54,   54,   54,   54,
   71,   71,    0,   71,   58,    0,    0,   70,   58,   58,
   58,   58,   58,   59,   58,    0,    0,   59,   59,   59,
   59,   59,    0,   59,    0,   85,   58,   58,    0,   58,
    0,    0,    0,    0,   71,   59,   59,    0,   59,   60,
   83,    0,   83,   60,   60,   60,   60,   60,  127,   60,
    0,    0,  148,  125,  123,    0,  124,  130,  126,    0,
   58,   60,   60,    0,   60,   83,   83,    0,   85,   59,
    0,  129,    0,  128,  127,   83,    0,    0,  165,  125,
  123,    0,  124,  130,  126,    0,   12,   13,   14,   15,
   16,   17,    0,    0,    0,   60,    0,  129,    0,  128,
  127,    0,  131,    0,  167,  125,  123,    0,  124,  130,
  126,  127,    0,   18,    0,  170,  125,  123,    0,  124,
  130,  126,    0,  129,    0,  128,    0,    0,  131,    0,
    0,    0,  127,    0,  129,    0,  128,  125,  123,  174,
  124,  130,  126,    0,   85,    0,   85,    0,    0,    0,
    0,    0,    0,    0,  131,  129,    0,  128,  127,    0,
    0,    0,  175,  125,  123,  131,  124,  130,  126,   85,
   85,    0,    0,    0,    0,    0,   62,   62,    0,   85,
    0,  129,    0,  128,  127,    0,  131,    0,  176,  125,
  123,    0,  124,  130,  126,    0,   70,   70,    0,    0,
   70,   70,   70,   70,    0,    0,    0,  129,    0,  128,
    0,    0,  131,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   71,   71,    0,    0,   71,   71,   71,
   71,    0,    0,    0,    0,    0,    0,    0,  131,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
   58,    0,    0,   58,   58,   58,   58,    0,   59,   59,
    0,    0,   59,   59,   59,   59,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,   60,    0,    0,   60,   60,
   60,   60,    0,  117,  118,    0,    0,  119,  120,  121,
  122,    0,    0,    0,    0,  127,    0,    0,    0,    0,
  125,  123,    0,  124,  130,  126,    0,    0,    0,  117,
  118,    0,    0,  119,  120,  121,  122,  190,  129,    0,
  128,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,    0,    0,   65,    0,  117,  118,    0,    0,  119,
  120,  121,  122,    0,    0,    0,  117,  118,   65,  131,
  119,  120,  121,  122,  127,    0,    0,    0,    0,  125,
  123,    0,  124,  130,  126,    0,    0,  117,  118,    0,
    0,  119,  120,  121,  122,    0,    0,  129,  127,  128,
    0,    0,   65,  125,  123,    0,  124,  130,  126,    0,
    0,    0,    0,  117,  118,    0,    0,  119,  120,  121,
  122,  129,    0,  128,    0,    0,  127,    0,  131,    0,
  178,  125,  123,    0,  124,  130,  126,    0,    0,  117,
  118,    0,    0,  119,  120,  121,  122,    0,  207,  129,
  127,  128,  131,    0,  184,  125,  123,    0,  124,  130,
  126,  127,    0,    0,    0,    0,  125,  123,    0,  124,
  130,  126,  208,  129,    0,  128,   66,    0,    0,   66,
  131,    0,   53,    0,  129,    0,  128,   53,   53,    0,
   53,   53,   53,  127,   66,    0,    0,    0,  125,  123,
    0,  124,  130,  126,  131,   53,    0,   53,    0,    0,
    0,    0,    0,    0,  127,  131,  129,    0,  128,  125,
  123,    0,  124,  130,  126,    0,    0,   56,   66,   56,
   56,   56,    0,    0,    0,    0,   53,  129,   57,  128,
   57,   57,   57,    0,    0,   56,   56,  131,   56,    0,
    0,   64,    0,    0,   64,   63,   57,   57,   63,   57,
  117,  118,    0,    0,  119,  120,  121,  122,  131,   64,
    0,    0,    0,   63,    0,    0,    0,    0,    0,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   57,   65,   65,    0,    0,    0,    0,   65,   65,    0,
    0,    0,    0,   64,    0,    0,    0,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  117,
  118,    0,    0,  119,  120,  121,  122,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  117,  118,    0,    0,  119,  120,  121,
  122,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  117,  118,    0,    0,  119,  120,  121,  122,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  117,  118,    0,    0,  119,
  120,  121,  122,    0,    0,    0,  117,  118,    0,    0,
  119,  120,  121,  122,    0,    0,    0,   66,   66,    0,
    0,    0,    0,   66,   66,    0,    0,   53,   53,    0,
    0,   53,   53,   53,   53,    0,    0,    0,  117,    0,
    0,    0,  119,  120,  121,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  119,  120,  121,  122,    0,   56,   56,
    0,    0,   56,   56,   56,   56,    0,    0,    0,   57,
   57,    0,    0,   57,   57,   57,   57,    0,    0,    0,
    0,    0,   64,   64,   92,    0,   63,   63,   64,   64,
    0,    0,   63,   63,    0,    0,  104,  105,  107,    0,
  108,  109,  110,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  133,
    0,  135,    0,    0,    0,    0,    0,  138,  139,    0,
    0,  139,  144,  145,  146,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  149,  150,  151,
  152,  153,  154,  155,  156,  157,  158,  159,  160,  161,
  162,    0,  163,    0,    0,    0,    0,    0,  169,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  139,    0,  182,    0,    0,
    0,    0,  186,    0,    0,    0,    0,    0,  188,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  205,    0,  206,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,  262,   37,   89,   40,  281,   59,   42,
   43,   45,   45,   46,   47,   41,   91,   91,   91,  100,
   41,   46,   39,   37,   41,   59,  264,   60,   42,   62,
   64,   41,   46,   47,   33,  281,   35,   36,   59,  281,
  123,   40,   37,   59,   40,  263,   45,   42,   43,   93,
   45,   46,   47,  165,   11,  167,   41,  275,   91,   44,
   41,   18,  280,   44,   41,   64,   91,   44,   30,   33,
   32,   35,   36,   40,   44,   41,   40,   91,   40,  191,
   41,   45,   59,  164,  123,   40,   40,   40,  200,  123,
   33,  125,   35,   36,   40,   40,   91,   40,   55,   40,
   64,   40,   45,   59,   41,   41,  190,   44,   44,   41,
   40,   33,   44,   35,   36,   40,   93,   40,   40,   40,
  281,   64,   59,   45,  123,   41,  125,   61,   44,   40,
   59,   59,   33,   59,   35,   36,   41,   41,  281,   40,
   59,   91,   64,   59,   45,   40,   59,   41,  257,  258,
  259,  260,  261,  262,   41,  123,   93,  281,   41,  123,
   37,   41,   44,   64,   41,   42,   43,   44,   45,   46,
   47,   93,  281,  270,   41,    0,  125,   93,   59,   58,
   58,  123,   59,   60,   61,   62,   41,   37,   41,  281,
   59,   41,   42,   43,   41,   45,   46,   47,    3,  257,
  258,  259,  260,  261,  262,   11,  281,  281,  281,   59,
   60,   32,   62,  193,   91,   -1,   93,   -1,   -1,   -1,
   -1,   -1,  281,  257,  258,  259,  260,  261,  262,  263,
  281,  265,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   91,  276,  277,  278,  279,  280,  281,   -1,   -1,
   -1,  285,   -1,  286,  287,  281,  290,  291,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
  269,  281,  271,  272,  273,  274,   -1,  276,  277,  278,
  279,  280,   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,
   -1,  290,  291,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,  269,  283,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  280,   -1,   -1,  262,
  263,  285,  265,  266,   -1,   -1,  290,  291,   -1,   -1,
  273,  274,   -1,   -1,  277,  278,   -1,  280,   -1,   -1,
   -1,  263,  285,  265,  266,  282,  283,  290,  291,   -1,
   -1,  273,  274,   -1,   -1,  277,  278,   -1,  280,   -1,
   -1,   46,  263,  285,  265,  266,  282,  283,  290,  291,
   -1,   -1,  273,  274,   -1,   41,  277,  278,   44,  280,
   -1,   -1,   -1,   -1,  285,   -1,   -1,   -1,   -1,  290,
  291,   37,   -1,   59,  125,   41,   42,   43,   44,   45,
   -1,   47,   -1,   -1,   89,  282,  283,   -1,   -1,  286,
  287,  288,  289,   59,   60,   -1,   62,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   93,   47,   -1,
   -1,   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,
   59,   60,   -1,   62,   37,   -1,   -1,   93,   41,   42,
   43,   44,   45,   37,   47,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,   -1,   46,   59,   60,   -1,   62,
   -1,   -1,   -1,   -1,   93,   59,   60,   -1,   62,   37,
  165,   -1,  167,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   93,   59,   60,   -1,   62,  190,  191,   -1,   89,   93,
   -1,   60,   -1,   62,   37,  200,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,  257,  258,  259,  260,
  261,  262,   -1,   -1,   -1,   93,   -1,   60,   -1,   62,
   37,   -1,   91,   -1,   41,   42,   43,   -1,   45,   46,
   47,   37,   -1,  284,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   60,   -1,   62,   -1,   -1,   91,   -1,
   -1,   -1,   37,   -1,   60,   -1,   62,   42,   43,   44,
   45,   46,   47,   -1,  165,   -1,  167,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   60,   -1,   62,   37,   -1,
   -1,   -1,   41,   42,   43,   91,   45,   46,   47,  190,
  191,   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,  200,
   -1,   60,   -1,   62,   37,   -1,   91,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,  282,  283,   -1,   -1,
  286,  287,  288,  289,   -1,   -1,   -1,   60,   -1,   62,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,  286,  287,
  288,  289,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   -1,  282,  283,   -1,   -1,  286,
  287,  288,  289,   -1,   -1,   -1,  282,  283,   59,   91,
  286,  287,  288,  289,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,  282,  283,   -1,
   -1,  286,  287,  288,  289,   -1,   -1,   60,   37,   62,
   -1,   -1,   93,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   60,   -1,   62,   -1,   -1,   37,   -1,   91,   -1,
   93,   42,   43,   -1,   45,   46,   47,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,   59,   60,
   37,   62,   91,   -1,   93,   42,   43,   -1,   45,   46,
   47,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   59,   60,   -1,   62,   41,   -1,   -1,   44,
   91,   -1,   37,   -1,   60,   -1,   62,   42,   43,   -1,
   45,   46,   47,   37,   59,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   91,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   91,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   41,   93,   43,
   44,   45,   -1,   -1,   -1,   -1,   91,   60,   41,   62,
   43,   44,   45,   -1,   -1,   59,   60,   91,   62,   -1,
   -1,   41,   -1,   -1,   44,   41,   59,   60,   44,   62,
  282,  283,   -1,   -1,  286,  287,  288,  289,   91,   59,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   93,  282,  283,   -1,   -1,   -1,   -1,  288,  289,   -1,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  282,  283,   -1,   -1,  286,  287,  288,
  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,  286,
  287,  288,  289,   -1,   -1,   -1,  282,  283,   -1,   -1,
  286,  287,  288,  289,   -1,   -1,   -1,  282,  283,   -1,
   -1,   -1,   -1,  288,  289,   -1,   -1,  282,  283,   -1,
   -1,  286,  287,  288,  289,   -1,   -1,   -1,  282,   -1,
   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  286,  287,  288,  289,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,  282,
  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,   -1,  282,  283,   53,   -1,  282,  283,  288,  289,
   -1,   -1,  288,  289,   -1,   -1,   65,   66,   67,   -1,
   69,   70,   71,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   88,
   -1,   90,   -1,   -1,   -1,   -1,   -1,   96,   97,   -1,
   -1,  100,  101,  102,  103,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  116,  117,  118,
  119,  120,  121,  122,  123,  124,  125,  126,  127,  128,
  129,   -1,  131,   -1,   -1,   -1,   -1,   -1,  137,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  164,   -1,  166,   -1,   -1,
   -1,   -1,  171,   -1,   -1,   -1,   -1,   -1,  177,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  201,   -1,  203,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,"'@'",null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"COMPLEX","CLASS","NULL","EXTENDS","THIS","SUPER","WHILE","FOR","IF","ELSE",
"RETURN","BREAK","NEW","CASE","DEFAULT","PRINT","READ_INTEGER","READ_LINE",
"PRINTCOMP","LITERAL","IDENTIFIER","AND","OR","STATIC","INSTANCEOF",
"LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","DCOPY","SCOPY","UMINUS",
"EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : COMPLEX",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : PrintCompStmt ';'",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"DefaultExpr : DEFAULT ':' Expr ';'",
"CaseExprList : CaseExprList Constant ':' Expr ';'",
"CaseExprList :",
"Expr : DCOPY '(' Expr ')'",
"Expr : SCOPY '(' Expr ')'",
"Expr : SUPER",
"Expr : CASE '(' Expr ')' '{' CaseExprList DefaultExpr '}'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : '@' Expr",
"Expr : '$' Expr",
"Expr : '#' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"PrintCompStmt : PRINTCOMP '(' ExprList ')'",
};

//#line 488 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 686 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 62 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 66 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 76 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 82 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 86 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 98 "Parser.y"
{
				yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
			}
break;
case 11:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 106 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 112 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 118 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 122 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 128 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 132 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 136 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 144 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 151 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 155 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 162 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 166 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 172 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 178 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 182 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 189 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 194 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 210 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 214 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 218 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 225 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 231 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 238 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 244 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 253 "Parser.y"
{
				yyval.expr = val_peek(1).expr;
			}
break;
case 47:
//#line 260 "Parser.y"
{
				yyval.caseConstList.add(val_peek(3).expr);
				yyval.caseExprList.add(val_peek(1).expr);
			}
break;
case 48:
//#line 265 "Parser.y"
{
				yyval = new SemValue();
				yyval.caseConstList = new ArrayList<Expr>();
				yyval.caseExprList = new ArrayList<Expr>();
			}
break;
case 49:
//#line 273 "Parser.y"
{
				yyval.expr = new Tree.DCopyExpr(val_peek(1).expr, val_peek(3).loc);
			}
break;
case 50:
//#line 277 "Parser.y"
{
				yyval.expr = new Tree.SCopyExpr(val_peek(1).expr, val_peek(3).loc);
			}
break;
case 51:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
			}
break;
case 52:
//#line 285 "Parser.y"
{
				yyval.expr = new Tree.Case(
						val_peek(5).expr, val_peek(2).caseConstList, val_peek(2).caseExprList, val_peek(1).expr, val_peek(7).loc
					);
			}
break;
case 53:
//#line 291 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 56:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 349 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 70:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 75:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 76:
//#line 377 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 77:
//#line 381 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 78:
//#line 385 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 79:
//#line 389 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 80:
//#line 393 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 81:
//#line 397 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 83:
//#line 407 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 85:
//#line 414 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 86:
//#line 421 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 87:
//#line 425 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 88:
//#line 432 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 89:
//#line 438 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 90:
//#line 444 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 91:
//#line 450 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 92:
//#line 456 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 93:
//#line 460 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 94:
//#line 466 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 95:
//#line 470 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 96:
//#line 476 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 97:
//#line 482 "Parser.y"
{
				yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
			}
break;
//#line 1350 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
