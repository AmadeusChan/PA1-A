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
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short PRINTCOMP=276;
public final static short LITERAL=277;
public final static short IDENTIFIER=278;
public final static short AND=279;
public final static short OR=280;
public final static short STATIC=281;
public final static short INSTANCEOF=282;
public final static short LESS_EQUAL=283;
public final static short GREATER_EQUAL=284;
public final static short EQUAL=285;
public final static short NOT_EQUAL=286;
public final static short UMINUS=287;
public final static short EMPTY=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   27,   27,   26,   26,   28,   28,
   16,   17,   20,   15,   29,   29,   18,   18,   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    2,    2,    2,    3,    3,    1,
    4,    5,    6,    5,    1,    1,    1,    0,    3,    1,
    5,    9,    1,    6,    2,    0,    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   76,   70,    0,    0,
    0,    0,   83,    0,    0,    0,    0,    0,   75,    0,
    0,    0,    0,   25,    0,    0,    0,   28,   36,   26,
    0,   30,   31,   32,    0,    0,    0,    0,    0,    0,
    0,    0,   48,    0,    0,    0,   46,    0,   47,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,   66,   67,   29,   33,   34,   35,   37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   68,   69,    0,    0,    0,   62,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   71,    0,    0,
   89,   90,    0,    0,   44,    0,    0,   81,    0,    0,
   72,    0,    0,   74,   45,    0,    0,   84,   73,    0,
   85,    0,   82,
};
final static short yydgoto[] = {                          2,
    3,    4,   68,   21,   34,    8,   11,   23,   35,   36,
   69,   46,   70,   71,   72,   73,   74,   75,   76,   77,
   78,   87,   80,   89,   82,  166,   83,  132,  178,
};
final static short yysindex[] = {                      -247,
 -254,    0, -247,    0, -236,    0, -241,  -82,    0,    0,
   66,    0,    0,    0,    0,    0, -234, -120,    0,    0,
  -14,  -90,    0,    0,  -86,    0,    6,  -44,   21, -120,
    0, -120,    0,  -85,   40,   18,   41,    0,  -40, -120,
  -40,    0,    0,    0,    0,   -6,    0,    0,   53,   58,
   63,  603,    0,  -56,   71,   75,   81,   82,    0,   83,
  603,  603,  345,    0,  603,  603,  603,    0,    0,    0,
   46,    0,    0,    0,   70,   76,   85,   87,   86,  499,
    0, -128,    0,  603,  603,  603,    0,  499,    0,  112,
   62,  603,  113,  115,  603,  603,  -25,  -25, -121,  317,
    0,    0,    0,    0,    0,    0,    0,    0,  603,  603,
  603,  603,  603,  603,  603,  603,  603,  603,  603,  603,
  603,  603,    0,  603,  126,  380,  108,  404,  132,  351,
  499,   -9,    0,    0,   13,  415,  134,    0,  499,  698,
  533,  751,  751,  730,  730,  -28,  -28,  -25,  -25,  -25,
  751,  751,  436,  603,   20,  603,   20,    0,  447,  603,
    0,    0, -102,  603,    0,  138,  137,    0,  471,  -84,
    0,  499,  142,    0,    0,  603,   20,    0,    0,  143,
    0,   20,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  199,    0,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  149,    0,    0,  168,
    0,  168,    0,    0,    0,  169,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -55,    0,    0,    0,    0,
    0,  -42,    0,    0,    0,    0,    0,    0,    0,    0,
  -64,  -64,  -64,    0,  -64,  -64,  -64,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  521,    0,
   54,    0,    0,  -64,  -55,  -64,    0,  157,    0,    0,
    0,  -64,    0,    0,  -64,  -64,   65,   89,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -64,  -64,
  -64,  -64,  -64,  -64,  -64,  -64,  -64,  -64,  -64,  -64,
  -64,  -64,    0,  -64,   27,    0,    0,    0,    0,  -64,
   34,    0,    0,    0,    0,    0,    0,    0,  -16,  312,
   -8,   35,  426,  617,  693,  609,  770,  118,  127,  153,
  428,  484,    0,  -31,  -55,  -64,  -55,    0,    0,  -64,
    0,    0,    0,  -64,    0,    0,  176,    0,    0,  -33,
    0,   36,    0,    0,    0,  -30,  -55,    0,    0,    0,
    0,  -55,    0,
};
final static short yygindex[] = {                         0,
    0,  215,  208,    8,    5,    0,    0,    0,  189,    0,
   11,    0, -135,  -72,    0,    0,    0,    0,    0,    0,
    0,  480,  838,  683,    0,    0,    0,  -87,    0,
};
final static int YYTABLESIZE=1056;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         86,
   28,   86,   86,   40,   28,   28,   86,  135,  120,   78,
   40,   86,  127,  118,    1,   22,   88,  123,  119,  168,
  123,  170,   25,    5,   38,   86,   62,    7,   67,   66,
   86,  161,   61,   63,  160,   61,    9,   33,   61,   33,
   10,  181,   38,   24,   26,   30,  183,   44,   31,   43,
   61,   45,   62,  162,   67,   66,  160,   65,   91,   63,
   32,   40,  124,   43,   61,  124,  167,   43,   43,   43,
   43,   43,   43,   43,   80,   58,   79,   80,   58,   79,
   39,   41,   42,   65,   61,   43,   43,   43,   43,   86,
   47,   86,   84,   58,   39,   47,   47,   85,   47,   47,
   47,   63,   86,  180,  104,   63,   63,   63,   63,   63,
   92,   63,   39,   47,   93,   47,   42,   43,   64,   43,
   94,   95,   96,   63,   63,   64,   63,   58,  105,   64,
   64,   64,   64,   64,  106,   64,   12,   13,   14,   15,
   16,   17,   42,  107,   47,  108,  109,   64,   64,  125,
   64,  129,  130,  133,   51,  134,  137,   63,   51,   51,
   51,   51,   51,   52,   51,  154,  156,   52,   52,   52,
   52,   52,  158,   52,  164,  173,   51,   51,  175,   51,
  160,   64,  179,  182,  177,   52,   52,   27,   52,   53,
   19,   29,   38,   53,   53,   53,   53,   53,    1,   53,
   12,   13,   14,   15,   16,   17,   15,    5,   20,   19,
   51,   53,   53,   42,   53,   87,   77,    6,   20,   52,
   37,   90,   42,   86,   86,   86,   86,   86,   86,   86,
    0,   86,   86,   86,   86,   42,   86,   86,   86,   86,
   86,   86,   86,   86,   86,   53,   42,   42,   86,    0,
   12,   13,   14,   15,   16,   17,   47,    0,   48,   49,
   50,   51,    0,   52,   53,   54,   55,   56,   57,   58,
   59,   61,    0,    0,    0,   60,   12,   13,   14,   15,
   16,   17,   47,    0,   48,   49,   50,   51,    0,   52,
   53,   54,   55,   56,   57,   58,   59,    0,    0,    0,
    0,   60,    0,    0,    0,   43,   43,    0,    0,   43,
   43,   43,   43,   58,   58,    0,    0,    0,    0,   58,
   58,    0,   12,   13,   14,   15,   16,   17,    0,    0,
    0,    0,   47,   47,    0,    0,   47,   47,   47,   47,
    0,    0,    0,   63,   63,    0,   18,   63,   63,   63,
   63,    0,   60,  120,    0,   60,    0,  138,  118,  116,
    0,  117,  123,  119,    0,    0,    0,   64,   64,    0,
   60,   64,   64,   64,   64,    0,  122,   62,  121,   67,
   66,    0,    0,   62,   63,   67,   66,    0,    0,   61,
   63,    0,    0,    0,    0,   61,   51,   51,    0,    0,
   51,   51,   51,   51,   60,   52,   52,  124,   65,   52,
   52,   52,   52,    0,   65,    0,  120,    0,    0,    0,
  155,  118,  116,    0,  117,  123,  119,    0,    0,    0,
    0,   53,   53,    0,    0,   53,   53,   53,   53,  122,
  120,  121,    0,   31,  157,  118,  116,    0,  117,  123,
  119,  120,    0,    0,    0,    0,  118,  116,  163,  117,
  123,  119,    0,  122,    0,  121,   59,    0,   57,   59,
  124,   57,  120,    0,  122,    0,  121,  118,  116,    0,
  117,  123,  119,  120,   59,    0,   57,    0,  118,  116,
    0,  117,  123,  119,  124,  122,    0,  121,    0,    0,
    0,    0,    0,    0,    0,  124,  122,  120,  121,    0,
    0,    0,  118,  116,    0,  117,  123,  119,   59,    0,
   57,    0,    0,    0,   56,   79,  124,   56,  165,  176,
  122,    0,  121,    0,    0,  120,    0,  124,    0,  171,
  118,  116,   56,  117,  123,  119,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,  122,    0,
  121,  124,   46,   46,   79,   46,   46,   46,    0,  120,
    0,    0,    0,    0,  118,  116,   56,  117,  123,  119,
   46,    0,   46,    0,    0,    0,    0,    0,    0,  124,
   60,   60,  122,    0,  121,  110,  111,    0,    0,  112,
  113,  114,  115,    0,    0,    0,   99,   47,    0,   48,
    0,   46,    0,   47,    0,   48,   54,    0,   56,   57,
    0,   59,   54,  124,   56,   57,   60,   59,    0,    0,
    0,    0,   60,    0,   79,   62,   79,   67,   66,    0,
    0,    0,   63,    0,    0,    0,    0,   61,    0,   49,
    0,   49,   49,   49,    0,   79,   79,   54,  110,  111,
   54,   79,  112,  113,  114,  115,   65,   49,   49,    0,
   49,    0,    0,    0,    0,   54,    0,    0,    0,    0,
    0,    0,  110,  111,    0,    0,  112,  113,  114,  115,
    0,    0,    0,  110,  111,    0,    0,  112,  113,  114,
  115,   49,    0,    0,   59,   59,   57,   57,    0,   54,
   59,   59,   57,   57,  110,  111,    0,    0,  112,  113,
  114,  115,    0,    0,    0,  110,  111,    0,   81,  112,
  113,  114,  115,   55,  120,    0,   55,    0,    0,  118,
  116,    0,  117,  123,  119,    0,    0,    0,    0,  110,
  111,   55,    0,  112,  113,  114,  115,  122,    0,  121,
    0,    0,   56,   56,    0,    0,  120,   81,   56,   56,
    0,  118,  116,    0,  117,  123,  119,  110,  111,    0,
    0,  112,  113,  114,  115,   55,    0,  120,  124,  122,
    0,  121,  118,  116,    0,  117,  123,  119,    0,   46,
   46,    0,    0,   46,   46,   46,   46,    0,    0,    0,
   50,  110,   50,   50,   50,  112,  113,  114,  115,    0,
  124,    0,    0,    0,    0,    0,    0,    0,   50,   50,
    0,   50,    0,    0,    0,    0,    0,   81,    0,   81,
    0,  124,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   81,   81,
    0,    0,   50,    0,   81,   47,    0,   48,    0,    0,
    0,    0,    0,    0,   54,    0,   56,   57,    0,   59,
    0,    0,    0,    0,   60,    0,    0,   49,   49,   88,
    0,   49,   49,   49,   49,   54,   54,    0,   97,   98,
  100,    0,  101,  102,  103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  126,    0,  128,    0,    0,    0,    0,    0,  131,
    0,    0,  131,  136,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  139,  140,  141,  142,
  143,  144,  145,  146,  147,  148,  149,  150,  151,  152,
    0,  153,    0,    0,    0,    0,    0,  159,    0,    0,
    0,   55,   55,    0,    0,    0,    0,    0,    0,    0,
  112,  113,  114,  115,    0,    0,    0,    0,    0,    0,
    0,  131,    0,  169,    0,    0,    0,  172,    0,    0,
    0,  174,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,  113,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   50,   50,
    0,    0,   50,   50,   50,   50,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   35,   36,   59,   91,   91,   40,   95,   37,   41,
   41,   45,   85,   42,  262,   11,   59,   46,   47,  155,
   46,  157,   18,  278,   41,   59,   33,  264,   35,   36,
   64,   41,   41,   40,   44,   44,  278,   30,   45,   32,
  123,  177,   59,  278,   59,   40,  182,   40,   93,   39,
   59,   41,   33,   41,   35,   36,   44,   64,   54,   40,
   40,   44,   91,   37,   45,   91,  154,   41,   42,   43,
   44,   45,   46,   47,   41,   41,   41,   44,   44,   44,
   41,   41,  123,   64,   93,   59,   60,   61,   62,  123,
   37,  125,   40,   59,   41,   42,   43,   40,   45,   46,
   47,   37,   40,  176,   59,   41,   42,   43,   44,   45,
   40,   47,   59,   60,   40,   62,  123,   91,  125,   93,
   40,   40,   40,   59,   60,   37,   62,   93,   59,   41,
   42,   43,   44,   45,   59,   47,  257,  258,  259,  260,
  261,  262,  123,   59,   91,   59,   61,   59,   60,  278,
   62,   40,   91,   41,   37,   41,  278,   93,   41,   42,
   43,   44,   45,   37,   47,   40,   59,   41,   42,   43,
   44,   45,   41,   47,   41,  278,   59,   60,   41,   62,
   44,   93,   41,   41,  269,   59,   60,  278,   62,   37,
  125,  278,  278,   41,   42,   43,   44,   45,    0,   47,
  257,  258,  259,  260,  261,  262,  123,   59,   41,   41,
   93,   59,   60,  278,   62,   59,   41,    3,   11,   93,
   32,  278,  278,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,  278,  270,  271,  272,  273,
  274,  275,  276,  277,  278,   93,  278,  278,  282,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
  267,  268,   -1,  270,  271,  272,  273,  274,  275,  276,
  277,  280,   -1,   -1,   -1,  282,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,  270,
  271,  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,
   -1,  282,   -1,   -1,   -1,  279,  280,   -1,   -1,  283,
  284,  285,  286,  279,  280,   -1,   -1,   -1,   -1,  285,
  286,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,  279,  280,   -1,  281,  283,  284,  285,
  286,   -1,   41,   37,   -1,   44,   -1,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,  279,  280,   -1,
   59,  283,  284,  285,  286,   -1,   60,   33,   62,   35,
   36,   -1,   -1,   33,   40,   35,   36,   -1,   -1,   45,
   40,   -1,   -1,   -1,   -1,   45,  279,  280,   -1,   -1,
  283,  284,  285,  286,   93,  279,  280,   91,   64,  283,
  284,  285,  286,   -1,   64,   -1,   37,   -1,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,   60,
   37,   62,   -1,   93,   41,   42,   43,   -1,   45,   46,
   47,   37,   -1,   -1,   -1,   -1,   42,   43,   44,   45,
   46,   47,   -1,   60,   -1,   62,   41,   -1,   41,   44,
   91,   44,   37,   -1,   60,   -1,   62,   42,   43,   -1,
   45,   46,   47,   37,   59,   -1,   59,   -1,   42,   43,
   -1,   45,   46,   47,   91,   60,   -1,   62,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   60,   37,   62,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   93,   -1,
   93,   -1,   -1,   -1,   41,   46,   91,   44,   93,   59,
   60,   -1,   62,   -1,   -1,   37,   -1,   91,   -1,   93,
   42,   43,   59,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,   60,   -1,
   62,   91,   42,   43,   85,   45,   46,   47,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   93,   45,   46,   47,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   91,
  279,  280,   60,   -1,   62,  279,  280,   -1,   -1,  283,
  284,  285,  286,   -1,   -1,   -1,  262,  263,   -1,  265,
   -1,   91,   -1,  263,   -1,  265,  272,   -1,  274,  275,
   -1,  277,  272,   91,  274,  275,  282,  277,   -1,   -1,
   -1,   -1,  282,   -1,  155,   33,  157,   35,   36,   -1,
   -1,   -1,   40,   -1,   -1,   -1,   -1,   45,   -1,   41,
   -1,   43,   44,   45,   -1,  176,  177,   41,  279,  280,
   44,  182,  283,  284,  285,  286,   64,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,  279,  280,   -1,   -1,  283,  284,  285,
  286,   93,   -1,   -1,  279,  280,  279,  280,   -1,   93,
  285,  286,  285,  286,  279,  280,   -1,   -1,  283,  284,
  285,  286,   -1,   -1,   -1,  279,  280,   -1,   46,  283,
  284,  285,  286,   41,   37,   -1,   44,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  279,
  280,   59,   -1,  283,  284,  285,  286,   60,   -1,   62,
   -1,   -1,  279,  280,   -1,   -1,   37,   85,  285,  286,
   -1,   42,   43,   -1,   45,   46,   47,  279,  280,   -1,
   -1,  283,  284,  285,  286,   93,   -1,   37,   91,   60,
   -1,   62,   42,   43,   -1,   45,   46,   47,   -1,  279,
  280,   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,
   41,  279,   43,   44,   45,  283,  284,  285,  286,   -1,
   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,  155,   -1,  157,
   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  176,  177,
   -1,   -1,   93,   -1,  182,  263,   -1,  265,   -1,   -1,
   -1,   -1,   -1,   -1,  272,   -1,  274,  275,   -1,  277,
   -1,   -1,   -1,   -1,  282,   -1,   -1,  279,  280,   52,
   -1,  283,  284,  285,  286,  279,  280,   -1,   61,   62,
   63,   -1,   65,   66,   67,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   84,   -1,   86,   -1,   -1,   -1,   -1,   -1,   92,
   -1,   -1,   95,   96,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  109,  110,  111,  112,
  113,  114,  115,  116,  117,  118,  119,  120,  121,  122,
   -1,  124,   -1,   -1,   -1,   -1,   -1,  130,   -1,   -1,
   -1,  279,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  154,   -1,  156,   -1,   -1,   -1,  160,   -1,   -1,
   -1,  164,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  279,  280,
   -1,   -1,  283,  284,  285,  286,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,"'#'","'$'","'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
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
"COMPLEX","CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","PRINTCOMP","LITERAL",
"IDENTIFIER","AND","OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL",
"EQUAL","NOT_EQUAL","UMINUS","EMPTY",
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

//#line 448 "Parser.y"
    
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
//#line 599 "Parser.java"
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
//#line 54 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 60 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 64 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 74 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 80 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 84 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 96 "Parser.y"
{
				yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
			}
break;
case 11:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 104 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 110 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 116 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 120 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 126 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 130 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 134 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 142 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 149 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 153 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 160 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 164 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 170 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 176 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 180 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 187 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 192 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 208 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 212 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 216 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 223 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 229 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 236 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 242 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 251 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 257 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 261 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 265 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 269 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 309 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 63:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 69:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 70:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 71:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 72:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 73:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 74:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 75:
//#line 363 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 76:
//#line 367 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 78:
//#line 374 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 79:
//#line 381 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 80:
//#line 385 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 81:
//#line 392 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 82:
//#line 398 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 83:
//#line 404 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 84:
//#line 410 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 85:
//#line 416 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 86:
//#line 420 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 87:
//#line 426 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 88:
//#line 430 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 89:
//#line 436 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 90:
//#line 442 "Parser.y"
{
				yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
			}
break;
//#line 1216 "Parser.java"
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
