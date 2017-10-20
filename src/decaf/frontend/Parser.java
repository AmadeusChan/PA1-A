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
   26,   26,   25,   25,   27,   27,   16,   17,   20,   15,
   28,   28,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    3,    1,    0,    2,
    0,    2,    4,    5,    1,    1,    1,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    3,    3,    1,    4,    5,    6,    5,
    1,    1,    1,    0,    3,    1,    5,    9,    1,    6,
    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   72,   66,    0,    0,
    0,    0,   79,    0,    0,    0,    0,   71,    0,    0,
    0,    0,   25,   28,   36,   26,    0,   30,   31,   32,
    0,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,   45,    0,   46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   29,   33,   34,   35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   64,   65,    0,    0,   61,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   67,    0,    0,   85,
    0,    0,   43,    0,    0,   77,    0,    0,   68,    0,
    0,   70,   44,    0,    0,   80,   69,    0,   81,    0,
   78,
};
final static short yydgoto[] = {                          2,
    3,    4,   64,   21,   34,    8,   11,   23,   35,   36,
   65,   46,   66,   67,   68,   69,   70,   71,   72,   73,
   82,   75,   84,   77,  154,   78,  122,  166,
};
final static short yysindex[] = {                      -249,
 -256,    0, -249,    0, -242,    0, -250,  -92,    0,    0,
  293,    0,    0,    0,    0,    0, -237, -149,    0,    0,
  -24,  -87,    0,    0,  -86,    0,    1,  -46,   11, -149,
    0, -149,    0,  -85,   15,   29,   45,    0,  -35, -149,
  -35,    0,    0,    0,    0,   -8,    0,    0,   49,   51,
   58,  369,    0,  261,   59,   66,   78,    0,   81,  369,
  369,  340,    0,    0,    0,    0,   41,    0,    0,    0,
   65,   67,   73,   74,  466,    0, -143,    0,  369,  369,
  369,    0,  466,    0,   96,   46,  369,   97,   98,  369,
  -38,  -38, -136,  286,    0,    0,    0,    0,  369,  369,
  369,  369,  369,  369,  369,  369,  369,  369,  369,  369,
  369,  369,    0,  369,  102,  312,   84,  378,  103,  377,
  466,   -5,    0,    0,  402,  106,    0,  466,  538,  498,
  -28,  -28,  564,  564,  -13,  -13,  -38,  -38,  -38,  -28,
  -28,  413,  369,   17,  369,   17,    0,  434,  369,    0,
 -128,  369,    0,  110,  108,    0,  455, -108,    0,  466,
  121,    0,    0,  369,   17,    0,    0,  127,    0,   17,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  170,    0,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  115,    0,    0,  135,
    0,  135,    0,    0,    0,  136,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,  -99,
  -99,  -99,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  487,    0,   34,    0,    0,  -99,  -58,
  -99,    0,  124,    0,    0,    0,  -99,    0,    0,  -99,
   60,   86,    0,    0,    0,    0,    0,    0,  -99,  -99,
  -99,  -99,  -99,  -99,  -99,  -99,  -99,  -99,  -99,  -99,
  -99,  -99,    0,  -99,   23,    0,    0,    0,    0,  -99,
    4,    0,    0,    0,    0,    0,    0,  -21,  163,    2,
  446,  515,  589,  595,  661,  715,  113,  122,  152,  576,
  578,    0,  -31,  -58,  -99,  -58,    0,    0,  -99,    0,
    0,  -99,    0,    0,  139,    0,    0,  -33,    0,   14,
    0,    0,    0,  -30,  -58,    0,    0,    0,    0,  -58,
    0,
};
final static short yygindex[] = {                         0,
    0,  182,  175,   12,    5,    0,    0,    0,  155,    0,
   33,    0, -116,  -77,    0,    0,    0,    0,    0,    0,
  663,  817,  706,    0,    0,    0,   55,    0,
};
final static int YYTABLESIZE=1000;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         82,
   39,   84,  117,   28,   28,   28,   82,  113,  110,   74,
   39,   82,    1,  108,  106,   22,  107,  113,  109,   37,
    5,    7,   25,  110,   61,   82,    9,  156,  108,  158,
   10,   62,  113,  109,   26,  150,   60,   37,  149,   24,
   30,   33,   60,   33,   76,   60,   31,   76,  169,   61,
   32,   44,  114,  171,   75,   39,   62,   75,   86,   42,
   60,   60,  114,   42,   42,   42,   42,   42,   42,   42,
   46,   43,   40,   45,   38,   46,   46,  114,   46,   46,
   46,   42,   42,   42,   42,   41,  168,   42,   79,   82,
   80,   82,   38,   46,   60,   46,   62,   81,   87,   95,
   62,   62,   62,   62,   62,   88,   62,   12,   13,   14,
   15,   16,   17,   42,   42,   42,   63,   89,   62,   62,
   90,   62,   63,   96,   46,   97,   63,   63,   63,   63,
   63,   98,   63,  115,   99,  119,  120,  123,  124,   42,
  126,  143,  145,  147,   63,   63,  152,   63,  161,   50,
  163,  149,   62,   50,   50,   50,   50,   50,   51,   50,
  165,  167,   51,   51,   51,   51,   51,  170,   51,    1,
   15,   50,   50,    5,   50,   20,   19,   41,   63,   73,
   51,   51,   83,   51,    6,   20,   37,    0,   52,   27,
   29,   38,   52,   52,   52,   52,   52,  155,   52,    0,
    0,    0,    0,   59,    0,   50,   59,    0,    0,    0,
   52,   52,    0,   52,   51,    0,    0,    0,   41,   41,
    0,   59,    0,   82,   82,   82,   82,   82,   82,   82,
    0,   82,   82,   82,   82,    0,   82,   82,   82,   82,
   82,   82,   82,   82,   52,   41,   41,   82,   12,   13,
   14,   15,   16,   17,   47,   59,   48,   49,   50,   51,
    0,   52,   53,   54,   55,   56,   57,   58,    0,    0,
    0,    0,   59,   12,   13,   14,   15,   16,   17,   47,
   60,   48,   49,   50,   51,    0,   52,   53,   54,   55,
   56,   57,   58,    0,    0,    0,    0,   59,    0,    0,
   42,   42,    0,    0,   42,   42,   42,   42,    0,    0,
    0,   46,   46,    0,    0,   46,   46,   46,   46,    0,
    0,    0,  110,    0,    0,    0,  127,  108,  106,    0,
  107,  113,  109,    0,    0,    0,    0,   62,   62,    0,
    0,   62,   62,   62,   62,  112,    0,  111,  110,    0,
    0,    0,  144,  108,  106,    0,  107,  113,  109,    0,
    0,    0,    0,   63,   63,    0,    0,   63,   63,   63,
   63,  112,   61,  111,    0,    0,  114,    0,    0,   62,
    0,    0,    0,    0,   60,    0,    0,    0,    0,    0,
   50,   50,    0,    0,   50,   50,   50,   50,    0,   51,
   51,   61,  114,   51,   51,   51,   51,    0,   62,   61,
    0,    0,    0,   60,  110,    0,   62,   19,  146,  108,
  106,   60,  107,  113,  109,    0,    0,    0,    0,   52,
   52,    0,    0,   52,   52,   52,   52,  112,  110,  111,
   59,   59,    0,  108,  106,  151,  107,  113,  109,  110,
    0,    0,    0,    0,  108,  106,    0,  107,  113,  109,
    0,  112,    0,  111,    0,    0,    0,    0,  114,   31,
  110,    0,  112,    0,  111,  108,  106,    0,  107,  113,
  109,    0,    0,    0,    0,    0,   57,    0,    0,   57,
    0,  110,  114,  112,    0,  111,  108,  106,    0,  107,
  113,  109,  110,  114,   57,  153,    0,  108,  106,    0,
  107,  113,  109,  164,  112,    0,  111,   12,   13,   14,
   15,   16,   17,   45,  114,  112,  159,  111,   45,   45,
    0,   45,   45,   45,  110,    0,    0,   85,   57,  108,
  106,    0,  107,  113,  109,  114,   45,    0,   45,   12,
   13,   14,   15,   16,   17,   58,  114,  112,   58,  111,
    0,    0,    0,  100,  101,    0,    0,  102,  103,  104,
  105,    0,   18,   58,  110,    0,    0,   45,    0,  108,
  106,    0,  107,  113,  109,    0,    0,    0,  114,  100,
  101,    0,    0,  102,  103,  104,  105,  112,    0,  111,
  110,   93,   47,    0,   48,  108,  106,   58,  107,  113,
  109,   54,    0,   56,   57,   58,   56,    0,   55,   56,
   59,   55,    0,  112,    0,  111,    0,    0,  114,   53,
    0,   47,   53,   48,   56,   54,   55,    0,   54,   47,
   54,   48,   56,   57,   58,    0,    0,   53,   54,   59,
   56,   57,   58,   54,  114,  100,  101,   59,    0,  102,
  103,  104,  105,    0,    0,    0,    0,    0,   56,    0,
   55,    0,    0,    0,    0,    0,    0,    0,    0,  100,
  101,   53,    0,  102,  103,  104,  105,   54,    0,    0,
  100,  101,    0,    0,  102,  103,  104,  105,    0,    0,
    0,   48,    0,   48,   48,   48,    0,    0,   74,    0,
    0,  100,  101,    0,    0,  102,  103,  104,  105,   48,
   48,    0,   48,   57,   57,    0,    0,    0,    0,   57,
   57,    0,  100,  101,    0,    0,  102,  103,  104,  105,
    0,    0,   74,  100,  101,    0,    0,  102,  103,  104,
  105,   76,    0,   48,    0,   49,    0,   49,   49,   49,
    0,    0,    0,    0,   45,   45,    0,    0,   45,   45,
   45,   45,    0,   49,   49,  100,   49,    0,    0,  102,
  103,  104,  105,    0,    0,   76,    0,    0,    0,    0,
    0,    0,   58,   58,    0,    0,    0,    0,   58,   58,
    0,    0,    0,    0,    0,    0,   74,   49,   74,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  102,
  103,  104,  105,    0,    0,    0,   74,   74,    0,    0,
    0,    0,   74,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  102,  103,    0,    0,   76,
    0,   76,    0,   56,   56,   55,   55,    0,    0,   56,
   56,   55,   55,    0,    0,    0,   53,   53,   83,   76,
   76,    0,   54,   54,    0,   76,   91,   92,   94,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  116,    0,  118,    0,    0,
    0,    0,    0,  121,    0,    0,  125,    0,    0,    0,
    0,    0,    0,    0,    0,  128,  129,  130,  131,  132,
  133,  134,  135,  136,  137,  138,  139,  140,  141,    0,
  142,    0,    0,    0,    0,    0,  148,    0,   48,   48,
    0,    0,   48,   48,   48,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  121,
    0,  157,    0,    0,    0,  160,    0,    0,  162,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,   49,    0,    0,   49,   49,   49,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   80,   91,   91,   91,   40,   46,   37,   41,
   41,   45,  262,   42,   43,   11,   45,   46,   47,   41,
  277,  264,   18,   37,   33,   59,  277,  144,   42,  146,
  123,   40,   46,   47,   59,   41,   45,   59,   44,  277,
   40,   30,   41,   32,   41,   44,   93,   44,  165,   33,
   40,   40,   91,  170,   41,   41,   40,   44,   54,   37,
   59,   45,   91,   41,   42,   43,   44,   45,   46,   47,
   37,   39,   44,   41,   41,   42,   43,   91,   45,   46,
   47,   59,   60,   61,   62,   41,  164,  123,   40,  123,
   40,  125,   59,   60,   93,   62,   37,   40,   40,   59,
   41,   42,   43,   44,   45,   40,   47,  257,  258,  259,
  260,  261,  262,   91,  123,   93,  125,   40,   59,   60,
   40,   62,   37,   59,   91,   59,   41,   42,   43,   44,
   45,   59,   47,  277,   61,   40,   91,   41,   41,  123,
  277,   40,   59,   41,   59,   60,   41,   62,  277,   37,
   41,   44,   93,   41,   42,   43,   44,   45,   37,   47,
  269,   41,   41,   42,   43,   44,   45,   41,   47,    0,
  123,   59,   60,   59,   62,   41,   41,  277,   93,   41,
   59,   60,   59,   62,    3,   11,   32,   -1,   37,  277,
  277,  277,   41,   42,   43,   44,   45,  143,   47,   -1,
   -1,   -1,   -1,   41,   -1,   93,   44,   -1,   -1,   -1,
   59,   60,   -1,   62,   93,   -1,   -1,   -1,  277,  277,
   -1,   59,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,   93,  277,  277,  281,  257,  258,
  259,  260,  261,  262,  263,   93,  265,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,  276,   -1,   -1,
   -1,   -1,  281,  257,  258,  259,  260,  261,  262,  263,
  279,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,  281,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   60,   -1,   62,   37,   -1,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   60,   33,   62,   -1,   -1,   91,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   45,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,  278,
  279,   33,   91,  282,  283,  284,  285,   -1,   40,   33,
   -1,   -1,   -1,   45,   37,   -1,   40,  125,   41,   42,
   43,   45,   45,   46,   47,   -1,   -1,   -1,   -1,  278,
  279,   -1,   -1,  282,  283,  284,  285,   60,   37,   62,
  278,  279,   -1,   42,   43,   44,   45,   46,   47,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,   93,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   -1,   37,   91,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   37,   91,   59,   93,   -1,   42,   43,   -1,
   45,   46,   47,   59,   60,   -1,   62,  257,  258,  259,
  260,  261,  262,   37,   91,   60,   93,   62,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,  277,   93,   42,
   43,   -1,   45,   46,   47,   91,   60,   -1,   62,  257,
  258,  259,  260,  261,  262,   41,   91,   60,   44,   62,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,  280,   59,   37,   -1,   -1,   91,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   91,  278,
  279,   -1,   -1,  282,  283,  284,  285,   60,   -1,   62,
   37,  262,  263,   -1,  265,   42,   43,   93,   45,   46,
   47,  272,   -1,  274,  275,  276,   41,   -1,   41,   44,
  281,   44,   -1,   60,   -1,   62,   -1,   -1,   91,   41,
   -1,  263,   44,  265,   59,   41,   59,   -1,   44,  263,
  272,  265,  274,  275,  276,   -1,   -1,   59,  272,  281,
  274,  275,  276,   59,   91,  278,  279,  281,   -1,  282,
  283,  284,  285,   -1,   -1,   -1,   -1,   -1,   93,   -1,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  278,
  279,   93,   -1,  282,  283,  284,  285,   93,   -1,   -1,
  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,   -1,
   -1,   41,   -1,   43,   44,   45,   -1,   -1,   46,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   59,
   60,   -1,   62,  278,  279,   -1,   -1,   -1,   -1,  284,
  285,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   80,  278,  279,   -1,   -1,  282,  283,  284,
  285,   46,   -1,   93,   -1,   41,   -1,   43,   44,   45,
   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   59,   60,  278,   62,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   80,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,  284,  285,
   -1,   -1,   -1,   -1,   -1,   -1,  144,   93,  146,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,   -1,  164,  165,   -1,   -1,
   -1,   -1,  170,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  282,  283,   -1,   -1,  144,
   -1,  146,   -1,  278,  279,  278,  279,   -1,   -1,  284,
  285,  284,  285,   -1,   -1,   -1,  278,  279,   52,  164,
  165,   -1,  278,  279,   -1,  170,   60,   61,   62,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   79,   -1,   81,   -1,   -1,
   -1,   -1,   -1,   87,   -1,   -1,   90,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   99,  100,  101,  102,  103,
  104,  105,  106,  107,  108,  109,  110,  111,  112,   -1,
  114,   -1,   -1,   -1,   -1,   -1,  120,   -1,  278,  279,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  143,
   -1,  145,   -1,   -1,   -1,  149,   -1,   -1,  152,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
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

//#line 427 "Parser.y"
    
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
//#line 578 "Parser.java"
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
//#line 52 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 58 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 62 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 72 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 78 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 82 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 86 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 94 "Parser.y"
{
				yyval.type = new Tree.TypeIdent(Tree.COMPLEX, val_peek(0).loc);
			}
break;
case 11:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 12:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 13:
//#line 108 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 114 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 118 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 124 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 128 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 132 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 147 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 151 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 158 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 162 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 168 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 174 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 178 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 185 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 190 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 37:
//#line 205 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 38:
//#line 209 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 39:
//#line 213 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 220 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 226 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 43:
//#line 233 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 44:
//#line 239 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 45:
//#line 248 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 48:
//#line 254 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 49:
//#line 258 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 262 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 266 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 270 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 274 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 278 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 282 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 286 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 290 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 294 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 298 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 302 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 306 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 62:
//#line 310 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 314 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 318 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 65:
//#line 322 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 66:
//#line 326 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 67:
//#line 330 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 68:
//#line 334 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 69:
//#line 338 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 70:
//#line 342 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 71:
//#line 348 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 72:
//#line 352 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 74:
//#line 359 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 75:
//#line 366 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 76:
//#line 370 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 77:
//#line 377 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 78:
//#line 383 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 79:
//#line 389 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 80:
//#line 395 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 81:
//#line 401 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 82:
//#line 405 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 83:
//#line 411 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 85:
//#line 421 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1171 "Parser.java"
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
