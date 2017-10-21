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
public final static short UMINUS=290;
public final static short EMPTY=291;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   27,   28,   28,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   29,
   29,   26,   26,   30,   30,   16,   17,   20,   15,   31,
   31,   18,   18,   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    3,    1,    0,
    2,    0,    2,    4,    5,    4,    5,    0,    1,    8,
    1,    1,    1,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    2,    2,    2,
    2,    2,    3,    3,    1,    4,    5,    6,    5,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   81,   75,   49,    0,
    0,    0,    0,   88,    0,    0,    0,    0,    0,    0,
   80,    0,    0,    0,    0,   25,    0,    0,    0,   28,
   36,   26,    0,   30,   31,   32,    0,    0,    0,    0,
    0,    0,    0,    0,   53,    0,    0,    0,   51,    0,
   52,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   70,   71,   72,   29,   33,   34,   35,
   37,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   41,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,   74,    0,    0,
    0,   67,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   76,    0,    0,    0,   94,   95,    0,    0,   44,
    0,    0,   86,    0,    0,   77,   48,    0,    0,   79,
   45,    0,    0,   89,    0,   78,    0,   90,    0,    0,
    0,    0,    0,   50,    0,   87,    0,    0,   46,   47,
};
final static short yydgoto[] = {                          2,
    3,    4,   70,   21,   34,    8,   11,   23,   35,   36,
   71,   46,   72,   73,   74,   75,   76,   77,   78,   79,
   80,   89,   82,   91,   84,  171,  190,  185,   85,  136,
  184,
};
final static short yysindex[] = {                      -249,
 -264,    0, -249,    0, -246,    0, -254,  -95,    0,    0,
  -82,    0,    0,    0,    0,    0, -240, -145,    0,    0,
  -12,  -86,    0,    0,  -85,    0,   12,  -40,   25, -145,
    0, -145,    0,  -77,   17,   26,   33,    0,  -38, -145,
  -38,    0,    0,    0,    0,   -3,    0,    0,    0,   58,
   62,   63,  515,    0,  278,   68,   79,   88,   90,   91,
    0,  100,  515,  515,  291,    0,  515,  515,  515,    0,
    0,    0,   45,    0,    0,    0,   73,   82,   94,   97,
   86,  700,    0, -132,    0,  515,  515,  515,    0,  700,
    0,  117,   67,  515,  515,  118,  121,  515,  515,  -27,
  -27, -116,  357,    0,    0,    0,    0,    0,    0,    0,
    0,  515,  515,  515,  515,  515,  515,  515,  515,  515,
  515,  515,  515,  515,  515,    0,  515,  126,  387,  108,
  409,  132,  328,  420,  700,   42,    0,    0,   43,  442,
  140,    0,  700,  783,  771,  -22,  -22,  826,  826,   -2,
   -2,  -27,  -27,  -27,  -22,  -22,  470,  515,   27,  515,
   27,    0,  481,   59,  515,    0,    0,  -98,  515,    0,
  143,  144,    0,  572,  -80,    0,    0,  700,  150,    0,
    0,  515,   27,    0, -225,    0,  151,    0,  147,   81,
  149,   27,  515,    0,  515,    0,  619,  674,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  208,    0,   87,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  152,    0,    0,  168,
    0,  168,    0,    0,    0,  171,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,    0,  -50,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -68,  -68,  -68,    0,  -68,  -68,  -68,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  759,    0,   64,    0,    0,  -68,  -58,  -68,    0,  155,
    0,    0,    0,  -68,  -68,    0,    0,  -68,  -68,   92,
  101,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -68,  -68,  -68,  -68,  -68,  -68,  -68,  -68,  -68,
  -68,  -68,  -68,  -68,  -68,    0,  -68,   34,    0,    0,
    0,    0,  -68,    0,   56,    0,    0,    0,    0,    0,
    0,    0,  -25,  367,   -5,  642,  646,  432,  490,  834,
  854,  127,  156,  298,  682,  794,    0,  -31,  -58,  -68,
  -58,    0,    0,    0,  -68,    0,    0,    0,  -68,    0,
    0,  176,    0,    0,  -33,    0,    0,   77,    0,    0,
    0,  -30,  -58,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,  -68,    0,  -68,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  216,  210,   16,   11,    0,    0,    0,  190,    0,
   18,    0, -110,  -83,    0,    0,    0,    0,    0,    0,
    0,  761, 1040,  793,    0,    0,    0,    0,   52,  -90,
    0,
};
final static int YYTABLESIZE=1235;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         91,
   40,   91,   91,  130,   28,   28,   91,  139,   93,   83,
   40,   91,    1,   28,  123,   38,    5,    7,  126,  121,
  119,   22,  120,  126,  122,   91,    9,   10,   25,   64,
   91,   69,   68,   38,  123,   66,   65,   47,   66,  121,
   24,   63,   19,  126,  122,   33,   26,   33,  173,  189,
  175,   30,   31,   66,   61,   44,   43,   39,   45,   64,
   67,   69,   68,  127,   32,   93,   65,  172,  127,   40,
   43,   63,  188,   41,   43,   43,   43,   43,   43,   43,
   43,  196,  166,  167,   42,  165,  165,   66,  127,   91,
   67,   91,   43,   43,   43,   43,   85,   86,  187,   85,
   52,   87,   88,  107,   39,   52,   52,   94,   52,   52,
   52,   12,   13,   14,   15,   16,   17,   84,   95,   42,
   84,   66,   39,   52,   43,   52,   43,   96,   68,   97,
   98,  108,   68,   68,   68,   68,   68,   69,   68,   99,
  109,   69,   69,   69,   69,   69,  112,   69,  128,   42,
   68,   68,  110,   68,   52,  111,  132,  133,  137,   69,
   69,  138,   69,   56,  141,  158,  160,   56,   56,   56,
   56,   56,  162,   56,   12,   13,   14,   15,   16,   17,
  169,  177,  179,  181,   68,   56,   56,  165,   56,  183,
  186,  192,   57,   69,   27,   29,   57,   57,   57,   57,
   57,   18,   57,   38,  193,  194,  195,    1,   20,   15,
    5,   19,   42,   92,   57,   57,   82,   57,    6,   56,
   20,   37,   42,   91,   91,   91,   91,   91,   91,   91,
   42,   91,   91,   91,   91,   91,  191,   91,   91,   91,
   91,    0,   91,   91,   91,   91,   91,   91,   57,   42,
   42,   91,    0,   12,   13,   14,   15,   16,   17,   47,
    0,   48,   49,   50,   51,   52,    0,   53,   54,   55,
   56,    0,   57,   58,   59,   60,   61,   66,    0,    0,
    0,   62,    0,   12,   13,   14,   15,   16,   17,   47,
    0,   48,   49,   50,   51,   52,    0,   53,   54,   55,
   56,    0,   57,   58,   59,   60,   61,    0,    0,    0,
    0,   62,    0,    0,    0,   43,   43,    0,    0,   43,
   43,   43,   43,   64,    0,   69,   68,    0,    0,    0,
   65,    0,    0,    0,   58,   63,    0,    0,   58,   58,
   58,   58,   58,    0,   58,   52,   52,    0,    0,   52,
   52,   52,   52,    0,   67,    0,   58,   58,    0,   58,
   64,    0,   69,   68,    0,    0,    0,   65,    0,    0,
    0,    0,   63,   68,   68,    0,    0,   68,   68,   68,
   68,    0,   69,   69,    0,    0,   69,   69,   69,   69,
   58,   67,    0,  123,    0,    0,    0,  142,  121,  119,
    0,  120,  126,  122,    0,    0,    0,   65,   56,   56,
   65,    0,   56,   56,   56,   56,  125,    0,  124,    0,
   31,    0,    0,  123,    0,   65,    0,  159,  121,  119,
    0,  120,  126,  122,    0,    0,    0,   57,   57,    0,
    0,   57,   57,   57,   57,  123,  125,  127,  124,  161,
  121,  119,    0,  120,  126,  122,  123,    0,    0,   65,
  164,  121,  119,    0,  120,  126,  122,    0,  125,    0,
  124,    0,   59,    0,    0,   59,    0,  127,  123,  125,
    0,  124,    0,  121,  119,  168,  120,  126,  122,    0,
   59,    0,    0,    0,    0,    0,    0,    0,    0,  127,
    0,  125,    0,  124,    0,    0,  123,    0,    0,    0,
  127,  121,  119,    0,  120,  126,  122,  123,    0,    0,
    0,    0,  121,  119,   59,  120,  126,  122,    0,  125,
   60,  124,  127,   60,   12,   13,   14,   15,   16,   17,
  125,    0,  124,    0,    0,    0,    0,   64,   60,   69,
   68,    0,  102,   47,   65,   48,   49,    0,   92,   63,
  127,    0,  170,   55,   56,    0,    0,   58,   59,    0,
   61,  127,    0,  176,    0,   62,    0,    0,   67,   58,
   58,    0,   60,   58,   58,   58,   58,    0,    0,    0,
   47,    0,   48,   49,    0,    0,    0,    0,    0,    0,
   55,   56,    0,    0,   58,   59,    0,   61,  123,    0,
    0,    0,   62,  121,  119,    0,  120,  126,  122,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  182,  125,    0,  124,    0,    0,    0,    0,  113,  114,
    0,    0,  115,  116,  117,  118,    0,    0,   65,   65,
    0,    0,    0,    0,    0,  123,    0,    0,    0,    0,
  121,  119,  127,  120,  126,  122,    0,    0,  113,  114,
    0,    0,  115,  116,  117,  118,    0,  199,  125,    0,
  124,    0,   63,    0,    0,   63,   64,    0,    0,   64,
  113,  114,    0,    0,  115,  116,  117,  118,    0,    0,
   63,  113,  114,    0,   64,  115,  116,  117,  118,  127,
  123,    0,    0,   59,   59,  121,  119,    0,  120,  126,
  122,    0,   62,  113,  114,   62,    0,  115,  116,  117,
  118,    0,  200,  125,   63,  124,  123,    0,   64,    0,
   62,  121,  119,    0,  120,  126,  122,    0,    0,    0,
    0,  113,  114,    0,    0,  115,  116,  117,  118,  125,
    0,  124,  113,  114,  127,    0,  115,  116,  117,  118,
    0,   60,   60,    0,   62,    0,    0,   47,    0,   48,
   49,    0,    0,    0,    0,    0,    0,   55,   56,    0,
  127,   58,   59,    0,   61,   51,    0,    0,    0,   62,
   51,   51,    0,   51,   51,   51,   81,  123,    0,    0,
    0,    0,  121,  119,    0,  120,  126,  122,   51,  123,
   51,    0,    0,    0,  121,  119,    0,  120,  126,  122,
  125,    0,  124,    0,   61,    0,    0,   61,   83,    0,
    0,    0,  125,    0,  124,    0,    0,   81,    0,   51,
    0,    0,   61,  113,  114,    0,    0,  115,  116,  117,
  118,  127,  123,    0,    0,    0,    0,  121,  119,    0,
  120,  126,  122,  127,   54,    0,   54,   54,   54,   83,
    0,    0,    0,    0,    0,  125,   61,  124,    0,    0,
    0,    0,   54,   54,   55,   54,   55,   55,   55,    0,
  113,  114,    0,    0,  115,  116,  117,  118,    0,    0,
    0,    0,   55,   55,    0,   55,  127,    0,    0,   81,
    0,   81,    0,   63,   63,    0,   54,   64,   64,   63,
   63,    0,    0,   64,   64,    0,    0,    0,    0,    0,
    0,    0,   81,   81,    0,    0,   55,    0,    0,    0,
    0,   83,   81,   83,    0,  113,  114,    0,    0,  115,
  116,  117,  118,   62,   62,    0,    0,    0,    0,   62,
   62,    0,    0,    0,   83,   83,    0,    0,    0,    0,
    0,  113,  114,    0,   83,  115,  116,  117,  118,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   51,    0,    0,   51,   51,   51,   51,    0,    0,
    0,    0,  113,    0,    0,    0,  115,  116,  117,  118,
    0,    0,    0,    0,    0,    0,    0,    0,  115,  116,
  117,  118,    0,    0,    0,   61,   61,    0,    0,    0,
    0,   61,   61,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   90,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  100,  101,  103,    0,  104,  105,  106,    0,
    0,  115,  116,    0,    0,   54,   54,    0,    0,   54,
   54,   54,   54,    0,    0,  129,    0,  131,    0,    0,
    0,    0,    0,  134,  135,   55,   55,  135,  140,   55,
   55,   55,   55,    0,    0,    0,    0,    0,    0,    0,
    0,  143,  144,  145,  146,  147,  148,  149,  150,  151,
  152,  153,  154,  155,  156,    0,  157,    0,    0,    0,
    0,    0,  163,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  135,    0,  174,
    0,    0,    0,    0,  178,    0,    0,    0,  180,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  197,    0,  198,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   87,   91,   91,   40,   98,   59,   41,
   41,   45,  262,   91,   37,   41,  281,  264,   46,   42,
   43,   11,   45,   46,   47,   59,  281,  123,   18,   33,
   64,   35,   36,   59,   37,   41,   40,  263,   44,   42,
  281,   45,  125,   46,   47,   30,   59,   32,  159,  275,
  161,   40,   93,   59,  280,   40,   39,   41,   41,   33,
   64,   35,   36,   91,   40,   55,   40,  158,   91,   44,
   37,   45,  183,   41,   41,   42,   43,   44,   45,   46,
   47,  192,   41,   41,  123,   44,   44,   93,   91,  123,
   64,  125,   59,   60,   61,   62,   41,   40,  182,   44,
   37,   40,   40,   59,   41,   42,   43,   40,   45,   46,
   47,  257,  258,  259,  260,  261,  262,   41,   40,  123,
   44,  125,   59,   60,   91,   62,   93,   40,   37,   40,
   40,   59,   41,   42,   43,   44,   45,   37,   47,   40,
   59,   41,   42,   43,   44,   45,   61,   47,  281,  123,
   59,   60,   59,   62,   91,   59,   40,   91,   41,   59,
   60,   41,   62,   37,  281,   40,   59,   41,   42,   43,
   44,   45,   41,   47,  257,  258,  259,  260,  261,  262,
   41,  123,  281,   41,   93,   59,   60,   44,   62,  270,
   41,   41,   37,   93,  281,  281,   41,   42,   43,   44,
   45,  284,   47,  281,   58,  125,   58,    0,   41,  123,
   59,   41,  281,   59,   59,   60,   41,   62,    3,   93,
   11,   32,  281,  257,  258,  259,  260,  261,  262,  263,
  281,  265,  266,  267,  268,  269,  185,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  280,  281,   93,  281,
  281,  285,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  280,  283,   -1,   -1,
   -1,  285,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,  269,   -1,  271,  272,  273,
  274,   -1,  276,  277,  278,  279,  280,   -1,   -1,   -1,
   -1,  285,   -1,   -1,   -1,  282,  283,   -1,   -1,  286,
  287,  288,  289,   33,   -1,   35,   36,   -1,   -1,   -1,
   40,   -1,   -1,   -1,   37,   45,   -1,   -1,   41,   42,
   43,   44,   45,   -1,   47,  282,  283,   -1,   -1,  286,
  287,  288,  289,   -1,   64,   -1,   59,   60,   -1,   62,
   33,   -1,   35,   36,   -1,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   45,  282,  283,   -1,   -1,  286,  287,  288,
  289,   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,
   93,   64,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   41,  282,  283,
   44,   -1,  286,  287,  288,  289,   60,   -1,   62,   -1,
   93,   -1,   -1,   37,   -1,   59,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,  282,  283,   -1,
   -1,  286,  287,  288,  289,   37,   60,   91,   62,   41,
   42,   43,   -1,   45,   46,   47,   37,   -1,   -1,   93,
   41,   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,
   62,   -1,   41,   -1,   -1,   44,   -1,   91,   37,   60,
   -1,   62,   -1,   42,   43,   44,   45,   46,   47,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   60,   -1,   62,   -1,   -1,   37,   -1,   -1,   -1,
   91,   42,   43,   -1,   45,   46,   47,   37,   -1,   -1,
   -1,   -1,   42,   43,   93,   45,   46,   47,   -1,   60,
   41,   62,   91,   44,  257,  258,  259,  260,  261,  262,
   60,   -1,   62,   -1,   -1,   -1,   -1,   33,   59,   35,
   36,   -1,  262,  263,   40,  265,  266,   -1,  281,   45,
   91,   -1,   93,  273,  274,   -1,   -1,  277,  278,   -1,
  280,   91,   -1,   93,   -1,  285,   -1,   -1,   64,  282,
  283,   -1,   93,  286,  287,  288,  289,   -1,   -1,   -1,
  263,   -1,  265,  266,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,   -1,   -1,  277,  278,   -1,  280,   37,   -1,
   -1,   -1,  285,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   -1,   -1,  282,  283,
   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,   -1,   -1,
   42,   43,   91,   45,   46,   47,   -1,   -1,  282,  283,
   -1,   -1,  286,  287,  288,  289,   -1,   59,   60,   -1,
   62,   -1,   41,   -1,   -1,   44,   41,   -1,   -1,   44,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,
   59,  282,  283,   -1,   59,  286,  287,  288,  289,   91,
   37,   -1,   -1,  282,  283,   42,   43,   -1,   45,   46,
   47,   -1,   41,  282,  283,   44,   -1,  286,  287,  288,
  289,   -1,   59,   60,   93,   62,   37,   -1,   93,   -1,
   59,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,  282,  283,   -1,   -1,  286,  287,  288,  289,   60,
   -1,   62,  282,  283,   91,   -1,  286,  287,  288,  289,
   -1,  282,  283,   -1,   93,   -1,   -1,  263,   -1,  265,
  266,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,   -1,
   91,  277,  278,   -1,  280,   37,   -1,   -1,   -1,  285,
   42,   43,   -1,   45,   46,   47,   46,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   60,   37,
   62,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   60,   -1,   62,   -1,   41,   -1,   -1,   44,   46,   -1,
   -1,   -1,   60,   -1,   62,   -1,   -1,   87,   -1,   91,
   -1,   -1,   59,  282,  283,   -1,   -1,  286,  287,  288,
  289,   91,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   91,   41,   -1,   43,   44,   45,   87,
   -1,   -1,   -1,   -1,   -1,   60,   93,   62,   -1,   -1,
   -1,   -1,   59,   60,   41,   62,   43,   44,   45,   -1,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   91,   -1,   -1,  159,
   -1,  161,   -1,  282,  283,   -1,   93,  282,  283,  288,
  289,   -1,   -1,  288,  289,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  182,  183,   -1,   -1,   93,   -1,   -1,   -1,
   -1,  159,  192,  161,   -1,  282,  283,   -1,   -1,  286,
  287,  288,  289,  282,  283,   -1,   -1,   -1,   -1,  288,
  289,   -1,   -1,   -1,  182,  183,   -1,   -1,   -1,   -1,
   -1,  282,  283,   -1,  192,  286,  287,  288,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  282,  283,   -1,   -1,  286,  287,  288,  289,   -1,   -1,
   -1,   -1,  282,   -1,   -1,   -1,  286,  287,  288,  289,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  286,  287,
  288,  289,   -1,   -1,   -1,  282,  283,   -1,   -1,   -1,
   -1,  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   53,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   63,   64,   65,   -1,   67,   68,   69,   -1,
   -1,  286,  287,   -1,   -1,  282,  283,   -1,   -1,  286,
  287,  288,  289,   -1,   -1,   86,   -1,   88,   -1,   -1,
   -1,   -1,   -1,   94,   95,  282,  283,   98,   99,  286,
  287,  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  112,  113,  114,  115,  116,  117,  118,  119,  120,
  121,  122,  123,  124,  125,   -1,  127,   -1,   -1,   -1,
   -1,   -1,  133,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  158,   -1,  160,
   -1,   -1,   -1,   -1,  165,   -1,   -1,   -1,  169,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  193,   -1,  195,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=291;
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
"LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","UMINUS","EMPTY",
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

//#line 479 "Parser.y"
    
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
//#line 650 "Parser.java"
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
//#line 55 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 61 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 65 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 75 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 81 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 85 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 97 "Parser.y"
{
				yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
			}
break;
case 11:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 105 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 117 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 121 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 131 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 135 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 143 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 150 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 154 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 165 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 171 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 177 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 181 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 188 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 193 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 209 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 213 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 217 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 224 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 230 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 237 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 243 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 252 "Parser.y"
{
				yyval.expr = val_peek(1).expr;
			}
break;
case 47:
//#line 259 "Parser.y"
{
				yyval.caseConstList.add(val_peek(3).expr);
				yyval.caseExprList.add(val_peek(1).expr);
			}
break;
case 48:
//#line 264 "Parser.y"
{
				yyval = new SemValue();
				yyval.caseConstList = new ArrayList<Expr>();
				yyval.caseExprList = new ArrayList<Expr>();
			}
break;
case 49:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.SuperExpr(val_peek(0).loc);
			}
break;
case 50:
//#line 276 "Parser.y"
{
				yyval.expr = new Tree.Case(
						val_peek(5).expr, val_peek(2).caseConstList, val_peek(2).caseExprList, val_peek(1).expr, val_peek(7).loc
					);
			}
break;
case 51:
//#line 282 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 54:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 340 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 68:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 356 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 360 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 364 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 368 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 372 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 376 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 380 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 384 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 388 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 394 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 398 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 405 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 412 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 416 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 423 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 89:
//#line 441 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 90:
//#line 447 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 91:
//#line 451 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 92:
//#line 457 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 93:
//#line 461 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 94:
//#line 467 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 95:
//#line 473 "Parser.y"
{
				yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
			}
break;
//#line 1302 "Parser.java"
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
