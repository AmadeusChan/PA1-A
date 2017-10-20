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
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short UMINUS=286;
public final static short EMPTY=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   14,   14,   14,   24,
   24,   21,   21,   23,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   26,   26,   25,   25,   27,   27,   16,
   17,   20,   15,   28,   28,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    3,    1,    0,    2,
    0,    2,    4,    5,    1,    1,    1,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    2,    2,    2,    3,    3,    1,    4,
    5,    6,    5,    1,    1,    1,    0,    3,    1,    5,
    9,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   75,   69,    0,    0,
    0,    0,   82,    0,    0,    0,    0,   74,    0,    0,
    0,    0,   25,    0,    0,    0,   28,   36,   26,    0,
   30,   31,   32,    0,    0,    0,    0,    0,    0,    0,
   47,    0,    0,    0,   45,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,   65,   66,
   29,   33,   34,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   68,
    0,    0,   61,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   70,    0,    0,   88,    0,    0,   43,    0,
    0,   80,    0,    0,   71,    0,    0,   73,   44,    0,
    0,   83,   72,    0,   84,    0,   81,
};
final static short yydgoto[] = {                          2,
    3,    4,   67,   21,   34,    8,   11,   23,   35,   36,
   68,   46,   69,   70,   71,   72,   73,   74,   75,   76,
   85,   78,   87,   80,  160,   81,  128,  172,
};
final static short yysindex[] = {                      -245,
 -247,    0, -245,    0, -228,    0, -229,  -69,    0,    0,
   68,    0,    0,    0,    0,    0, -222,  -56,    0,    0,
   -1,  -86,    0,    0,  -85,    0,   19,  -16,   39,  -56,
    0,  -56,    0,  -78,   45,   36,   46,    0,  -35,  -56,
  -35,    0,    0,    0,    0,   -8,    0,    0,   49,   53,
   54,  596,    0, -186,   58,   67,   70,    0,   82,  596,
  596,  349,    0,  596,  596,  596,    0,    0,    0,   40,
    0,    0,    0,   64,   72,   73,   77,  498,    0, -133,
    0,  596,  596,  596,    0,  498,    0,  101,   55,  596,
  106,  107,  596,  -28,  -28, -128,  272,    0,    0,    0,
    0,    0,    0,    0,  596,  596,  596,  596,  596,  596,
  596,  596,  596,  596,  596,  596,  596,  596,    0,  596,
  113,  379,   97,  403,  117,  545,  498,  -22,    0,    0,
  414,  125,    0,  498,  696,  555,   66,   66,  724,  724,
  -13,  -13,  -28,  -28,  -28,   66,   66,  435,  596,   17,
  596,   17,    0,  446,  596,    0, -110,  596,    0,  132,
  131,    0,  470,  -90,    0,  498,  140,    0,    0,  596,
   17,    0,    0,  141,    0,   17,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  183,    0,   61,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  129,    0,    0,  166,
    0,  166,    0,    0,    0,  167,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -55,    0,    0,    0,    0,    0,    0,    0,  -68,
  -68,  -68,    0,  -68,  -68,  -68,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  522,    0,   59,    0,
    0,  -68,  -58,  -68,    0,  151,    0,    0,    0,  -68,
    0,    0,  -68,   83,   92,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -68,  -68,  -68,  -68,  -68,  -68,
  -68,  -68,  -68,  -68,  -68,  -68,  -68,  -68,    0,  -68,
   23,    0,    0,    0,    0,  -68,    1,    0,    0,    0,
    0,    0,    0,  -21,  295,    2,  425,  427,  575,  706,
  607,  752,  118,  127,  153,  563,  630,    0,  -32,  -58,
  -68,  -58,    0,    0,  -68,    0,    0,  -68,    0,    0,
  173,    0,    0,  -33,    0,    3,    0,    0,    0,  -30,
  -58,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  213,  206,    9,   -3,    0,    0,    0,  186,    0,
  -18,    0, -136,  -73,    0,    0,    0,    0,    0,    0,
  680,  841,  705,    0,    0,    0,   74,    0,
};
final static int YYTABLESIZE=1037;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   39,   85,   85,   87,   28,   28,   85,   22,   77,  123,
   39,   85,   28,  162,   25,  164,    1,  119,  156,   37,
   43,  155,   45,  116,   61,   85,   66,   65,  114,    5,
   85,   62,  119,  115,  175,    7,   60,   37,   33,  177,
   33,   79,   60,   78,   79,   60,   78,    9,   44,   61,
   89,   66,   65,   10,   24,   64,   62,   26,   30,   42,
   60,   60,  120,   42,   42,   42,   42,   42,   42,   42,
   12,   13,   14,   15,   16,   17,   31,  120,   32,   40,
   64,   42,   42,   42,   42,   39,   41,   42,   82,   85,
   88,   85,   83,   84,   60,   46,  174,   90,  101,   38,
   46,   46,  116,   46,   46,   46,   91,  114,  112,   92,
  113,  119,  115,   42,   42,   42,   63,   38,   46,   62,
   46,   93,  102,   62,   62,   62,   62,   62,   63,   62,
  103,  104,   63,   63,   63,   63,   63,  105,   63,   42,
  125,   62,   62,  121,   62,  126,  129,  130,  132,   46,
   63,   63,  149,   63,   50,  151,  120,  153,   50,   50,
   50,   50,   50,   51,   50,  158,  167,   51,   51,   51,
   51,   51,  169,   51,  155,   62,   50,   50,  171,   50,
  173,  176,    1,   15,   63,   51,   51,    5,   51,   52,
   27,   29,   19,   52,   52,   52,   52,   52,   38,   52,
   12,   13,   14,   15,   16,   17,   20,   19,   41,   86,
   50,   52,   52,   76,   52,    6,   20,   37,   41,   51,
    0,   41,  161,   85,   85,   85,   85,   85,   85,   85,
    0,   85,   85,   85,   85,    0,   85,   85,   85,   85,
   85,   85,   85,   85,   41,   52,   41,   85,   12,   13,
   14,   15,   16,   17,   47,    0,   48,   49,   50,   51,
    0,   52,   53,   54,   55,   56,   57,   58,    0,    0,
    0,    0,   59,   12,   13,   14,   15,   16,   17,   47,
   60,   48,   49,   50,   51,    0,   52,   53,   54,   55,
   56,   57,   58,    0,    0,    0,    0,   59,    0,    0,
   42,   42,    0,    0,   42,   42,   42,   42,  116,    0,
    0,    0,  133,  114,  112,    0,  113,  119,  115,    0,
    0,    0,    0,    0,   12,   13,   14,   15,   16,   17,
    0,  118,    0,  117,    0,   59,   46,   46,   59,    0,
   46,   46,   46,   46,    0,    0,    0,   18,    0,    0,
    0,    0,    0,   59,    0,    0,    0,    0,    0,    0,
   62,   62,  120,    0,   62,   62,   62,   62,    0,   63,
   63,    0,    0,   63,   63,   63,   63,    0,    0,    0,
    0,   61,    0,   66,   65,    0,    0,   59,   62,    0,
    0,    0,    0,   60,    0,   50,   50,    0,    0,   50,
   50,   50,   50,    0,   51,   51,    0,    0,   51,   51,
   51,   51,   64,    0,    0,  116,    0,    0,    0,  150,
  114,  112,    0,  113,  119,  115,    0,    0,    0,    0,
   52,   52,    0,    0,   52,   52,   52,   52,  118,  116,
  117,    0,    0,  152,  114,  112,    0,  113,  119,  115,
  116,    0,    0,    0,    0,  114,  112,  157,  113,  119,
  115,    0,  118,    0,  117,   57,    0,   58,   57,  120,
   58,  116,    0,  118,    0,  117,  114,  112,    0,  113,
  119,  115,  116,   57,    0,   58,    0,  114,  112,    0,
  113,  119,  115,  120,  118,    0,  117,    0,    0,    0,
    0,    0,    0,    0,  120,  118,  116,  117,    0,    0,
    0,  114,  112,    0,  113,  119,  115,   57,    0,   58,
    0,    0,    0,    0,    0,  120,    0,  159,  170,  118,
    0,  117,    0,    0,  116,    0,  120,    0,  165,  114,
  112,    0,  113,  119,  115,    0,    0,    0,    0,  106,
  107,    0,    0,  108,  109,  110,  111,  118,   45,  117,
  120,    0,    0,   45,   45,    0,   45,   45,   45,    0,
    0,    0,   59,   59,    0,    0,    0,   61,    0,   66,
   65,   45,    0,   45,   62,    0,    0,    0,  120,   60,
    0,  116,    0,    0,    0,    0,  114,  112,    0,  113,
  119,  115,    0,   56,    0,    0,   56,    0,   64,    0,
   96,   47,   45,   48,  118,   53,  117,    0,   53,    0,
   54,   56,   56,   57,   58,    0,    0,    0,   61,   59,
   66,   65,    0,   53,    0,   62,    0,   31,    0,    0,
   60,    0,    0,    0,    0,  120,    0,   48,    0,   48,
   48,   48,    0,    0,    0,   56,  106,  107,    0,   64,
  108,  109,  110,  111,    0,   48,   48,   53,   48,    0,
   55,    0,    0,   55,    0,    0,    0,    0,    0,    0,
  106,  107,    0,    0,  108,  109,  110,  111,   55,    0,
    0,  106,  107,    0,    0,  108,  109,  110,  111,   48,
    0,    0,   57,   57,   58,   58,    0,    0,   57,   57,
   58,   58,  106,  107,    0,    0,  108,  109,  110,  111,
    0,    0,   55,  106,  107,   77,    0,  108,  109,  110,
  111,    0,  116,    0,    0,    0,    0,  114,  112,    0,
  113,  119,  115,    0,    0,    0,   54,  106,  107,   54,
   79,  108,  109,  110,  111,  118,    0,  117,    0,    0,
  116,    0,   77,    0,   54,  114,  112,    0,  113,  119,
  115,    0,    0,    0,    0,  106,  107,    0,    0,  108,
  109,  110,  111,  118,    0,  117,  120,   79,    0,    0,
    0,    0,   49,    0,   49,   49,   49,    0,   54,   45,
   45,    0,    0,   45,   45,   45,   45,   47,    0,   48,
   49,   49,    0,   49,  120,    0,   54,    0,   56,   57,
   58,    0,    0,    0,    0,   59,    0,    0,    0,   77,
    0,   77,  106,    0,    0,    0,  108,  109,  110,  111,
   56,   56,    0,    0,   49,    0,   56,   56,    0,   77,
   77,    0,   53,   53,   79,   77,   79,    0,   47,    0,
   48,    0,    0,    0,    0,    0,    0,   54,    0,   56,
   57,   58,    0,    0,   79,   79,   59,    0,    0,    0,
   79,    0,    0,    0,   48,   48,    0,    0,   48,   48,
   48,   48,   86,    0,    0,    0,    0,    0,    0,    0,
   94,   95,   97,    0,   98,   99,  100,   55,   55,    0,
    0,    0,    0,   55,   55,    0,    0,    0,    0,    0,
    0,    0,  122,    0,  124,    0,    0,    0,    0,    0,
  127,    0,    0,  131,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  134,  135,  136,  137,  138,
  139,  140,  141,  142,  143,  144,  145,  146,  147,    0,
  148,    0,    0,    0,    0,    0,  154,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  108,  109,  110,
  111,    0,    0,   54,   54,    0,    0,    0,    0,  127,
    0,  163,    0,    0,    0,  166,    0,    0,  168,    0,
    0,    0,    0,    0,    0,  108,  109,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
   49,    0,    0,   49,   49,   49,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   59,   91,   91,   40,   11,   41,   83,
   41,   45,   91,  150,   18,  152,  262,   46,   41,   41,
   39,   44,   41,   37,   33,   59,   35,   36,   42,  277,
   64,   40,   46,   47,  171,  264,   45,   59,   30,  176,
   32,   41,   41,   41,   44,   44,   44,  277,   40,   33,
   54,   35,   36,  123,  277,   64,   40,   59,   40,   37,
   59,   45,   91,   41,   42,   43,   44,   45,   46,   47,
  257,  258,  259,  260,  261,  262,   93,   91,   40,   44,
   64,   59,   60,   61,   62,   41,   41,  123,   40,  123,
  277,  125,   40,   40,   93,   37,  170,   40,   59,   41,
   42,   43,   37,   45,   46,   47,   40,   42,   43,   40,
   45,   46,   47,   91,  123,   93,  125,   59,   60,   37,
   62,   40,   59,   41,   42,   43,   44,   45,   37,   47,
   59,   59,   41,   42,   43,   44,   45,   61,   47,  123,
   40,   59,   60,  277,   62,   91,   41,   41,  277,   91,
   59,   60,   40,   62,   37,   59,   91,   41,   41,   42,
   43,   44,   45,   37,   47,   41,  277,   41,   42,   43,
   44,   45,   41,   47,   44,   93,   59,   60,  269,   62,
   41,   41,    0,  123,   93,   59,   60,   59,   62,   37,
  277,  277,  125,   41,   42,   43,   44,   45,  277,   47,
  257,  258,  259,  260,  261,  262,   41,   41,  277,   59,
   93,   59,   60,   41,   62,    3,   11,   32,  277,   93,
   -1,  277,  149,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,  277,   93,  277,  281,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,   -1,   -1,
   -1,   -1,  281,  257,  258,  259,  260,  261,  262,  263,
  279,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
   -1,   60,   -1,   62,   -1,   41,  278,  279,   44,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,  280,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   91,   -1,  282,  283,  284,  285,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
   -1,   33,   -1,   35,   36,   -1,   -1,   93,   40,   -1,
   -1,   -1,   -1,   45,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   64,   -1,   -1,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   60,   37,
   62,   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,
   37,   -1,   -1,   -1,   -1,   42,   43,   44,   45,   46,
   47,   -1,   60,   -1,   62,   41,   -1,   41,   44,   91,
   44,   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   37,   59,   -1,   59,   -1,   42,   43,   -1,
   45,   46,   47,   91,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   60,   37,   62,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   93,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   59,   60,
   -1,   62,   -1,   -1,   37,   -1,   91,   -1,   93,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   60,   37,   62,
   91,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,  278,  279,   -1,   -1,   -1,   33,   -1,   35,
   36,   60,   -1,   62,   40,   -1,   -1,   -1,   91,   45,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   41,   -1,   -1,   44,   -1,   64,   -1,
  262,  263,   91,  265,   60,   41,   62,   -1,   44,   -1,
  272,   59,  274,  275,  276,   -1,   -1,   -1,   33,  281,
   35,   36,   -1,   59,   -1,   40,   -1,   93,   -1,   -1,
   45,   -1,   -1,   -1,   -1,   91,   -1,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   93,  278,  279,   -1,   64,
  282,  283,  284,  285,   -1,   59,   60,   93,   62,   -1,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   59,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   93,
   -1,   -1,  278,  279,  278,  279,   -1,   -1,  284,  285,
  284,  285,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   93,  278,  279,   46,   -1,  282,  283,  284,
  285,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   41,  278,  279,   44,
   46,  282,  283,  284,  285,   60,   -1,   62,   -1,   -1,
   37,   -1,   83,   -1,   59,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   60,   -1,   62,   91,   83,   -1,   -1,
   -1,   -1,   41,   -1,   43,   44,   45,   -1,   93,  278,
  279,   -1,   -1,  282,  283,  284,  285,  263,   -1,  265,
   59,   60,   -1,   62,   91,   -1,  272,   -1,  274,  275,
  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,   -1,  150,
   -1,  152,  278,   -1,   -1,   -1,  282,  283,  284,  285,
  278,  279,   -1,   -1,   93,   -1,  284,  285,   -1,  170,
  171,   -1,  278,  279,  150,  176,  152,   -1,  263,   -1,
  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,   -1,  274,
  275,  276,   -1,   -1,  170,  171,  281,   -1,   -1,   -1,
  176,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   52,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   60,   61,   62,   -1,   64,   65,   66,  278,  279,   -1,
   -1,   -1,   -1,  284,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   82,   -1,   84,   -1,   -1,   -1,   -1,   -1,
   90,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  105,  106,  107,  108,  109,
  110,  111,  112,  113,  114,  115,  116,  117,  118,   -1,
  120,   -1,   -1,   -1,   -1,   -1,  126,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,  149,
   -1,  151,   -1,   -1,   -1,  155,   -1,   -1,  158,   -1,
   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
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
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"UMINUS","EMPTY",
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
};

//#line 441 "Parser.y"
    
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
//#line 589 "Parser.java"
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
case 37:
//#line 207 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 38:
//#line 211 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 39:
//#line 215 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 222 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 228 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 43:
//#line 235 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 44:
//#line 241 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 45:
//#line 250 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 48:
//#line 256 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 49:
//#line 260 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 264 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 268 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 276 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 280 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 284 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 308 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 62:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 68:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 69:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 70:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 71:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 72:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 73:
//#line 356 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 74:
//#line 362 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 75:
//#line 366 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 77:
//#line 373 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 78:
//#line 380 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 79:
//#line 384 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 80:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 81:
//#line 397 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1200 "Parser.java"
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
