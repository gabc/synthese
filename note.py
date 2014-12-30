from __future__ import division

Symbol = str          # A Lisp Symbol is implemented as a Python str
List   = list         # A Lisp List is implemented as a Python list
Number = (int, float) # A Lisp Number is implemented as a Python int or float

def parse(program):
    "Read a Scheme expression from a string."
    return read_from_tokens(tokenize(program))

def tokenize(s):
    "Convert a string into a list of tokens."
    return s.replace('(',' ( ').replace(')',' ) ').replace('=', '===').replace('lambda','function').split()

def read_from_tokens(tokens):
    "Read an expression from a sequence of tokens."
    if len(tokens) == 0:
        raise SyntaxError('unexpected EOF while reading')
    token = tokens.pop(0)
    if '(' == token:
        L = []
        while tokens[0] != ')':
            L.append(read_from_tokens(tokens))
        tokens.pop(0) # pop off ')'
        return L
    elif ')' == token:
        raise SyntaxError('unexpected )')
    else:
        return atom(token)

def atom(token):
    "Numbers become numbers; every other token is a symbol."
    try: return int(token)
    except ValueError:
        try: return float(token)
        except ValueError:
            return Symbol(token)

def print_expr(expr):
    if expr == []:
        return ""
    if isinstance(expr, Number):
        return str(expr)
    if isinstance(expr, str):
        return expr
    if expr[0] in ('+', '-','/','==='):          # as in (+,-,/,&&,=, etc)
        return print_expr(expr[1]) + " " + expr[0] + " " + print_expr(expr[2])
    if expr[0] == 'if':
        try:
            return "if(" + print_expr(expr[1])+") {" + print_expr(expr[2])+"} " + "else { " + print_expr(expr[3]) + "}" # if + test + then
        except:
            return "if(" + print_expr(expr[1])+") {" + print_expr(expr[2])+"}" # if + test + then
    if expr[0] == 'function':
        return expr[0] + '(' + print_expr(expr[1]) + ') {' + print_expr(expr[2]) + '}'
    if expr[0] == 'define':
        return 'var ' + print_expr(expr[1]) + ' = ' + print_expr(expr[2])
    return str(expr[0])
