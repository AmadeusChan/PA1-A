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
public final static short CASE=273;
public final static short DEFAULT=274;
public final static short PRINT=275;
public final static short READ_INTEGER=276;
public final static short READ_LINE=277;
public final static short PRINTCOMP=278;
public final static short LITERAL=279;
public final static short IDENTIFIER=280;
public final static short AND=281;
public final static short OR=282;
public final static short STATIC=283;
public final static short INSTANCEOF=284;
public final static short LESS_EQUAL=285;
public final static short GREATER_EQUAL=286;
public final static short EQUAL=287;
public final static short NOT_EQUAL=288;
public final static short UMINUS=289;
public final static short EMPTY=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   27,   28,   28,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   29,   29,
   26,   26,   30,   30,   16,   17,   20,   15,   31,   31,
   18,   18,   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    2,    3,    1,    0,
    2,    0,    2,    4,    5,    4,    5,    0,    8,    1,
    1,    1,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    2,    2,
    2,    3,    3,    1,    4,    5,    6,    5,    1,    1,
    1,    0,    3,    1,    5,    9,    1,    6,    2,    0,
    2,    1,    4,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    7,    8,    6,    9,   10,    0,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   80,   74,    0,    0,
    0,    0,   87,    0,    0,    0,    0,    0,    0,   79,
    0,    0,    0,    0,   25,    0,    0,    0,   28,   36,
   26,    0,   30,   31,   32,    0,    0,    0,    0,    0,
    0,    0,    0,   52,    0,    0,    0,   50,    0,   51,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   69,   70,   71,   29,   33,   34,   35,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   72,   73,    0,    0,    0,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   75,    0,    0,    0,   93,   94,    0,    0,   44,    0,
    0,   85,    0,    0,   76,   48,    0,    0,   78,   45,
    0,    0,   88,    0,   77,    0,   89,    0,    0,    0,
    0,    0,   49,    0,   86,    0,    0,   46,   47,
};
final static short yydgoto[] = {                          2,
    3,    4,   69,   21,   34,    8,   11,   23,   35,   36,
   70,   46,   71,   72,   73,   74,   75,   76,   77,   78,
   79,   88,   81,   90,   83,  170,  189,  184,   84,  135,
  183,
};
final static short yysindex[] = {                      -253,
 -267,    0, -253,    0, -247,    0, -258,  -72,    0,    0,
  -82,    0,    0,    0,    0,    0, -246,   66,    0,    0,
  -21,  -87,    0,    0,  -85,    0,    1,  -47,   16,   66,
    0,   66,    0,  -77,   29,   42,   57,    0,  -20,   66,
  -20,    0,    0,    0,    0,   -3,    0,    0,   62,   68,
   72,  652,    0,  -49,   75,   76,   78,   79,   81,    0,
   88,  652,  652,  583,    0,  652,  652,  652,    0,    0,
    0,   45,    0,    0,    0,   54,   71,   82,   90,   70,
  588,    0, -140,    0,  652,  652,  652,    0,  588,    0,
  107,   65,  652,  652,  112,  116,  652,  652,  -26,  -26,
 -122,  356,    0,    0,    0,    0,    0,    0,    0,    0,
  652,  652,  652,  652,  652,  652,  652,  652,  652,  652,
  652,  652,  652,  652,    0,  652,  119,  384,  103,  408,
  124,  616,  419,  588,  -23,    0,    0,   14,  440,  126,
    0,  588,  787,  761,  -18,  -18,  859,  859,   -2,   -2,
  -26,  -26,  -26,  -18,  -18,  451,  652,   27,  652,   27,
    0,  472,   50,  652,    0,    0,  -99,  652,    0,  141,
  139,    0,  508,  -79,    0,    0,  588,  143,    0,    0,
  652,   27,    0, -210,    0,  147,    0,  133,   80,  146,
   27,  652,    0,  652,    0,  529,  555,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  206,    0,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  157,    0,    0,  177,
    0,  177,    0,    0,    0,  178,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -44,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -59,  -59,  -59,    0,  -59,  -59,  -59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  737,
    0,   64,    0,    0,  -59,  -58,  -59,    0,  164,    0,
    0,    0,  -59,  -59,    0,    0,  -59,  -59,   92,  101,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -59,  -59,  -59,  -59,  -59,  -59,  -59,  -59,  -59,  -59,
  -59,  -59,  -59,  -59,    0,  -59,   34,    0,    0,    0,
    0,  -59,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    7,  326,   -5,   73,  376,  480,  839,  463,  726,
  127,  155,  297,  670,  827,    0,  -31,  -58,  -59,  -58,
    0,    0,    0,  -59,    0,    0,    0,  -59,    0,    0,
  200,    0,    0,  -33,    0,    0,   43,    0,    0,    0,
  -30,  -58,    0,    0,    0,    0,    0,    0,    0,    0,
  -58,  -59,    0,  -59,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  249,  242,   17,    5,    0,    0,    0,  229,    0,
   58,    0, -108,  -81,    0,    0,    0,    0,    0,    0,
    0,  495,  965,  727,    0,    0,    0,    0,   87,  -89,
    0,
};
final static int YYTABLESIZE=1159;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         90,
   40,   90,   90,   28,  129,   28,   90,  138,    1,   82,
   40,   90,    5,   28,   92,   22,    7,  165,  122,  125,
  164,    9,   25,  120,  118,   90,  119,  125,  121,   63,
   90,   68,   67,   24,  122,   65,   64,   26,   65,  120,
   30,   62,   19,  125,  121,   31,   33,   38,   33,  172,
   10,  174,   47,   65,  166,   32,   44,  164,   92,   63,
   66,   68,   67,  188,  126,   38,   64,  171,   60,   39,
   43,   62,  126,  187,   43,   43,   43,   43,   43,   43,
   43,   84,  195,   83,   84,   40,   83,   65,  126,   90,
   66,   90,   43,   43,   43,   43,   43,   41,   45,  186,
   51,   85,   42,  106,   39,   51,   51,   86,   51,   51,
   51,   87,  107,   62,   93,   94,   62,   95,   96,   42,
   97,   65,   39,   51,   43,   51,   43,   98,   67,  108,
  111,   62,   67,   67,   67,   67,   67,   68,   67,  127,
  109,   68,   68,   68,   68,   68,  131,   68,  110,   42,
   67,   67,  136,   67,   51,  132,  137,  140,  157,   68,
   68,  159,   68,   55,  161,   62,  168,   55,   55,   55,
   55,   55,  176,   55,   12,   13,   14,   15,   16,   17,
  178,  180,  164,  185,   67,   55,   55,  191,   55,  182,
  192,   56,   27,   68,   29,   56,   56,   56,   56,   56,
   18,   56,   38,  194,  193,    1,   15,   12,   13,   14,
   15,   16,   17,   56,   56,    5,   56,   20,   19,   55,
   42,   42,   91,   90,   90,   90,   90,   90,   90,   90,
   91,   90,   90,   90,   90,   42,   90,   90,   90,   90,
   81,   90,   90,   90,   90,   90,   90,   56,   42,   42,
   90,    6,   20,   12,   13,   14,   15,   16,   17,   47,
   37,   48,   49,   50,   51,    0,   52,   53,   54,   55,
  190,   56,   57,   58,   59,   60,   65,    0,    0,    0,
   61,    0,    0,   12,   13,   14,   15,   16,   17,   47,
    0,   48,   49,   50,   51,    0,   52,   53,   54,   55,
    0,   56,   57,   58,   59,   60,    0,    0,    0,    0,
   61,    0,    0,    0,   43,   43,    0,    0,   43,   43,
   43,   43,   12,   13,   14,   15,   16,   17,    0,    0,
    0,    0,    0,   57,    0,    0,    0,   57,   57,   57,
   57,   57,    0,   57,   51,   51,    0,    0,   51,   51,
   51,   51,    0,   62,   62,   57,   57,    0,   57,   62,
   62,    0,    0,    0,    0,    0,   64,    0,    0,   64,
    0,    0,   67,   67,    0,    0,   67,   67,   67,   67,
    0,   68,   68,    0,   64,   68,   68,   68,   68,   57,
    0,    0,  122,    0,    0,    0,  141,  120,  118,    0,
  119,  125,  121,    0,    0,    0,    0,   55,   55,    0,
    0,   55,   55,   55,   55,  124,   63,  123,   64,   63,
  122,    0,    0,    0,  158,  120,  118,    0,  119,  125,
  121,    0,    0,    0,   63,   56,   56,    0,    0,   56,
   56,   56,   56,  124,  122,  123,  126,    0,  160,  120,
  118,    0,  119,  125,  121,  122,    0,    0,    0,  163,
  120,  118,    0,  119,  125,  121,    0,  124,   63,  123,
    0,    0,    0,    0,  126,    0,  122,    0,  124,    0,
  123,  120,  118,  167,  119,  125,  121,  122,    0,    0,
    0,    0,  120,  118,    0,  119,  125,  121,  126,  124,
    0,  123,    0,   53,    0,   53,   53,   53,  122,  126,
  124,    0,  123,  120,  118,    0,  119,  125,  121,    0,
   58,   53,   53,   58,   53,    0,    0,    0,    0,    0,
  126,  124,    0,  123,    0,    0,    0,    0,   58,    0,
   80,  126,    0,  169,  122,    0,    0,    0,    0,  120,
  118,    0,  119,  125,  121,   53,    0,    0,    0,    0,
    0,    0,  126,    0,  175,  122,  181,  124,    0,  123,
  120,  118,   58,  119,  125,  121,    0,   57,   57,    0,
   80,   57,   57,   57,   57,    0,    0,  198,  124,    0,
  123,  122,    0,    0,    0,    0,  120,  118,  126,  119,
  125,  121,    0,    0,    0,    0,   64,   64,    0,    0,
    0,    0,    0,  199,  124,   63,  123,   68,   67,  126,
    0,    0,   64,    0,  122,    0,    0,   62,    0,  120,
  118,    0,  119,  125,  121,    0,  112,  113,    0,    0,
  114,  115,  116,  117,    0,  126,   66,  124,   63,  123,
   68,   67,   80,    0,   80,   64,   63,   63,    0,    0,
   62,    0,   63,   63,  112,  113,    0,    0,  114,  115,
  116,  117,    0,    0,    0,   80,   80,    0,  126,   66,
    0,    0,    0,    0,   63,   80,   68,   67,  112,  113,
    0,   64,  114,  115,  116,  117,   62,    0,    0,  112,
  113,    0,    0,  114,  115,  116,  117,    0,   31,    0,
   61,    0,    0,   61,    0,   66,    0,    0,    0,    0,
  112,  113,    0,    0,  114,  115,  116,  117,   61,    0,
    0,  112,  113,    0,    0,  114,  115,  116,  117,    0,
    0,    0,    0,   53,   53,    0,    0,   53,   53,   53,
   53,    0,  112,  113,    0,    0,  114,  115,  116,  117,
   58,   58,   61,    0,    0,    0,   54,    0,   54,   54,
   54,    0,   82,   50,    0,    0,    0,    0,   50,   50,
    0,   50,   50,   50,   54,   54,    0,   54,  112,  113,
    0,    0,  114,  115,  116,  117,   50,  122,   50,    0,
    0,    0,  120,  118,    0,  119,  125,  121,    0,  112,
  113,    0,   82,  114,  115,  116,  117,    0,   54,    0,
  124,    0,  123,  122,    0,    0,    0,   50,  120,  118,
    0,  119,  125,  121,    0,  112,  113,    0,    0,  114,
  115,  116,  117,    0,  101,   47,  124,   48,  123,    0,
    0,  126,    0,    0,   54,   55,    0,    0,   57,   58,
    0,   60,    0,    0,    0,    0,   61,   60,  112,  113,
   60,    0,  114,  115,  116,  117,    0,  126,   47,   59,
   48,    0,   59,    0,   82,   60,   82,   54,   55,    0,
    0,   57,   58,    0,   60,  122,    0,   59,    0,   61,
  120,  118,    0,  119,  125,  121,    0,   82,   82,    0,
    0,    0,    0,    0,   47,    0,   48,   82,  124,   60,
  123,    0,    0,   54,   55,    0,    0,   57,   58,    0,
   60,   59,    0,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  126,
   61,   61,    0,    0,    0,    0,   61,   61,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   54,   54,    0,    0,
   54,   54,   54,   54,    0,    0,   89,   50,   50,    0,
    0,   50,   50,   50,   50,    0,   99,  100,  102,    0,
  103,  104,  105,    0,    0,    0,    0,    0,    0,    0,
    0,  112,    0,    0,    0,  114,  115,  116,  117,  128,
    0,  130,    0,    0,    0,    0,    0,  133,  134,    0,
    0,  134,  139,    0,    0,    0,    0,    0,    0,    0,
    0,  114,  115,  116,  117,  142,  143,  144,  145,  146,
  147,  148,  149,  150,  151,  152,  153,  154,  155,    0,
  156,    0,    0,    0,    0,    0,  162,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   60,   60,    0,
    0,    0,    0,   60,   60,    0,    0,    0,    0,   59,
   59,  134,    0,  173,    0,    0,    0,    0,  177,    0,
    0,    0,  179,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  114,  115,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  196,    0,  197,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   35,   36,   91,   86,   91,   40,   97,  262,   41,
   41,   45,  280,   91,   59,   11,  264,   41,   37,   46,
   44,  280,   18,   42,   43,   59,   45,   46,   47,   33,
   64,   35,   36,  280,   37,   41,   40,   59,   44,   42,
   40,   45,  125,   46,   47,   93,   30,   41,   32,  158,
  123,  160,  263,   59,   41,   40,   40,   44,   54,   33,
   64,   35,   36,  274,   91,   59,   40,  157,  279,   41,
   37,   45,   91,  182,   41,   42,   43,   44,   45,   46,
   47,   41,  191,   41,   44,   44,   44,   93,   91,  123,
   64,  125,   59,   60,   61,   62,   39,   41,   41,  181,
   37,   40,  123,   59,   41,   42,   43,   40,   45,   46,
   47,   40,   59,   41,   40,   40,   44,   40,   40,  123,
   40,  125,   59,   60,   91,   62,   93,   40,   37,   59,
   61,   59,   41,   42,   43,   44,   45,   37,   47,  280,
   59,   41,   42,   43,   44,   45,   40,   47,   59,  123,
   59,   60,   41,   62,   91,   91,   41,  280,   40,   59,
   60,   59,   62,   37,   41,   93,   41,   41,   42,   43,
   44,   45,  123,   47,  257,  258,  259,  260,  261,  262,
  280,   41,   44,   41,   93,   59,   60,   41,   62,  269,
   58,   37,  280,   93,  280,   41,   42,   43,   44,   45,
  283,   47,  280,   58,  125,    0,  123,  257,  258,  259,
  260,  261,  262,   59,   60,   59,   62,   41,   41,   93,
  280,  280,   59,  257,  258,  259,  260,  261,  262,  263,
  280,  265,  266,  267,  268,  280,  270,  271,  272,  273,
   41,  275,  276,  277,  278,  279,  280,   93,  280,  280,
  284,    3,   11,  257,  258,  259,  260,  261,  262,  263,
   32,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  184,  275,  276,  277,  278,  279,  282,   -1,   -1,   -1,
  284,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
   -1,  275,  276,  277,  278,  279,   -1,   -1,   -1,   -1,
  284,   -1,   -1,   -1,  281,  282,   -1,   -1,  285,  286,
  287,  288,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   44,   45,   -1,   47,  281,  282,   -1,   -1,  285,  286,
  287,  288,   -1,  281,  282,   59,   60,   -1,   62,  287,
  288,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   -1,   -1,  281,  282,   -1,   -1,  285,  286,  287,  288,
   -1,  281,  282,   -1,   59,  285,  286,  287,  288,   93,
   -1,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  281,  282,   -1,
   -1,  285,  286,  287,  288,   60,   41,   62,   93,   44,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   59,  281,  282,   -1,   -1,  285,
  286,  287,  288,   60,   37,   62,   91,   -1,   41,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   60,   93,   62,
   -1,   -1,   -1,   -1,   91,   -1,   37,   -1,   60,   -1,
   62,   42,   43,   44,   45,   46,   47,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   91,   60,
   -1,   62,   -1,   41,   -1,   43,   44,   45,   37,   91,
   60,   -1,   62,   42,   43,   -1,   45,   46,   47,   -1,
   41,   59,   60,   44,   62,   -1,   -1,   -1,   -1,   -1,
   91,   60,   -1,   62,   -1,   -1,   -1,   -1,   59,   -1,
   46,   91,   -1,   93,   37,   -1,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   93,   37,   59,   60,   -1,   62,
   42,   43,   93,   45,   46,   47,   -1,  281,  282,   -1,
   86,  285,  286,  287,  288,   -1,   -1,   59,   60,   -1,
   62,   37,   -1,   -1,   -1,   -1,   42,   43,   91,   45,
   46,   47,   -1,   -1,   -1,   -1,  281,  282,   -1,   -1,
   -1,   -1,   -1,   59,   60,   33,   62,   35,   36,   91,
   -1,   -1,   40,   -1,   37,   -1,   -1,   45,   -1,   42,
   43,   -1,   45,   46,   47,   -1,  281,  282,   -1,   -1,
  285,  286,  287,  288,   -1,   91,   64,   60,   33,   62,
   35,   36,  158,   -1,  160,   40,  281,  282,   -1,   -1,
   45,   -1,  287,  288,  281,  282,   -1,   -1,  285,  286,
  287,  288,   -1,   -1,   -1,  181,  182,   -1,   91,   64,
   -1,   -1,   -1,   -1,   33,  191,   35,   36,  281,  282,
   -1,   40,  285,  286,  287,  288,   45,   -1,   -1,  281,
  282,   -1,   -1,  285,  286,  287,  288,   -1,   93,   -1,
   41,   -1,   -1,   44,   -1,   64,   -1,   -1,   -1,   -1,
  281,  282,   -1,   -1,  285,  286,  287,  288,   59,   -1,
   -1,  281,  282,   -1,   -1,  285,  286,  287,  288,   -1,
   -1,   -1,   -1,  281,  282,   -1,   -1,  285,  286,  287,
  288,   -1,  281,  282,   -1,   -1,  285,  286,  287,  288,
  281,  282,   93,   -1,   -1,   -1,   41,   -1,   43,   44,
   45,   -1,   46,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   59,   60,   -1,   62,  281,  282,
   -1,   -1,  285,  286,  287,  288,   60,   37,   62,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,  281,
  282,   -1,   86,  285,  286,  287,  288,   -1,   93,   -1,
   60,   -1,   62,   37,   -1,   -1,   -1,   91,   42,   43,
   -1,   45,   46,   47,   -1,  281,  282,   -1,   -1,  285,
  286,  287,  288,   -1,  262,  263,   60,  265,   62,   -1,
   -1,   91,   -1,   -1,  272,  273,   -1,   -1,  276,  277,
   -1,  279,   -1,   -1,   -1,   -1,  284,   41,  281,  282,
   44,   -1,  285,  286,  287,  288,   -1,   91,  263,   41,
  265,   -1,   44,   -1,  158,   59,  160,  272,  273,   -1,
   -1,  276,  277,   -1,  279,   37,   -1,   59,   -1,  284,
   42,   43,   -1,   45,   46,   47,   -1,  181,  182,   -1,
   -1,   -1,   -1,   -1,  263,   -1,  265,  191,   60,   93,
   62,   -1,   -1,  272,  273,   -1,   -1,  276,  277,   -1,
  279,   93,   -1,   -1,   -1,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
  281,  282,   -1,   -1,   -1,   -1,  287,  288,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  281,  282,   -1,   -1,
  285,  286,  287,  288,   -1,   -1,   52,  281,  282,   -1,
   -1,  285,  286,  287,  288,   -1,   62,   63,   64,   -1,
   66,   67,   68,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  281,   -1,   -1,   -1,  285,  286,  287,  288,   85,
   -1,   87,   -1,   -1,   -1,   -1,   -1,   93,   94,   -1,
   -1,   97,   98,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  285,  286,  287,  288,  111,  112,  113,  114,  115,
  116,  117,  118,  119,  120,  121,  122,  123,  124,   -1,
  126,   -1,   -1,   -1,   -1,   -1,  132,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  281,  282,   -1,
   -1,   -1,   -1,  287,  288,   -1,   -1,   -1,   -1,  281,
  282,  157,   -1,  159,   -1,   -1,   -1,   -1,  164,   -1,
   -1,   -1,  168,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  286,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  192,   -1,  194,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=290;
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
"COMPLEX","CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","CASE","DEFAULT","PRINT","READ_INTEGER","READ_LINE","PRINTCOMP",
"LITERAL","IDENTIFIER","AND","OR","STATIC","INSTANCEOF","LESS_EQUAL",
"GREATER_EQUAL","EQUAL","NOT_EQUAL","UMINUS","EMPTY",
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

//#line 475 "Parser.y"
    
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
//#line 632 "Parser.java"
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
				yyval.expr = new Tree.Case(
						val_peek(5).expr, val_peek(2).caseConstList, val_peek(2).caseExprList, val_peek(1).expr, val_peek(7).loc
					);
			}
break;
case 50:
//#line 278 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 53:
//#line 284 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 288 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 292 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 336 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 67:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPRE, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.GETCOMPIM, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 356 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.INT2COMP, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 360 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 73:
//#line 364 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 368 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 75:
//#line 372 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 76:
//#line 376 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 77:
//#line 380 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 78:
//#line 384 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 79:
//#line 390 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 80:
//#line 394 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 82:
//#line 401 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 83:
//#line 408 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 84:
//#line 412 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 87:
//#line 431 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 88:
//#line 437 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 89:
//#line 443 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 90:
//#line 447 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 91:
//#line 453 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 92:
//#line 457 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 93:
//#line 463 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 94:
//#line 469 "Parser.y"
{
				yyval.stmt = new PrintComp(val_peek(1).elist, val_peek(3).loc);
			}
break;
//#line 1278 "Parser.java"
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
