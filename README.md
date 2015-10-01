[Picobot]: https://www.cs.hmc.edu/twiki/bin/view/CS5/PicobotProgrammingGold
[Teams]: https://github.com/hmc-cs111-fall2015/piconot/wiki/Team-sign-ups
[API]: http://www.cs.hmc.edu/cs111/picolib/index.html#package

# Piconot

## Overview

This assignment asks you to re-design the syntax of an existing language
(Picobot) and to implement your syntax as an internal DSL. The goals of this
assignment are to: (a) learn how to design a brand-new syntax, and (b) get some
experience implementing a language the **wrong way**. You'll probably design a
syntax that is either difficult to implement internally or that is so different
from the semantics of the language that it requires significant implementation
effort on your part. **This is good!** It's good to have a chance to experience
the limitations of internal DSLs, so you'll start to develop an instinct for
when they are and are not appropriate. It's also good to have a chance to
experience how different a language's syntax can be from its semantics.

In short: get in there and play. Push internal DSLs to the limit! 

## Checklist
  - [ ] [Sign up for teams][Teams]. Team 1 will have three people; all other 
    teams will have two people
  - [ ] Design a new syntax for Picobot 
     - [ ] Describe your design in `design.md`
     - [ ] Before you implement the syntax, write the "empty room" program in
     `example-ideal.txt`
  - [ ] Implement your new syntax
     - [ ] Add files, as needed, to implement your syntax
     - Include two example programs
       - [ ] `src/main/scala/piconot/Empty.scala`
       - [ ] `src/main/scala/piconot/RightHand.scala`
     - [ ] Describe your implementation process in `evaluation.md`
  - [ ] Critique another team's design and implementation

## Warm-up: Picobot
You should (re-)familiarize yourself with 
[Picobot, as it's defined in CS 5][Picobot]. Make sure you understand the 
"empty room" Picobot program (posted on Piazza).

## Syntax design

Design your own syntax for Picobot. Try to come up with a design that is
faithful to the domain (of maze-navigation), that does not add any new features
(e.g., non-determinism), but that is also not necessarily restricted to students
in CS 5. The more novel your syntax, the better, assuming that it's faithful to
the domain. **Design your syntax as an ideal, i.e., don't worry at all about how
you'll implement it.**

**Note:** If you're in a group of three, you should come up with *two* designs.
You must describe and justify both designs, but you'll need to implement only
one of them.

Re-write the empty room program in your new syntax, in the file 
`example-ideal.txt`. **Note:** If you're in the group of three, put your two 
examples in this file.

Describe and justify your design, in the file `design.md` (see that file for
instructions on what to write). **Note:** If you're in a group of three,
describe both designs.

## Syntax implementation
Implement the syntax you designed, as an internal DSL in Scala. To do so, you'll
want to consider how you'll connect your syntax to the semantics of Picobot and 
how to organize your code. You'll also need to include a couple of sample
programs and keep a running "diary" of your work. **Note:** If you're in the 
group of three, you only need to implement one of the designs.

### Picobot semantics

I've provided a library that implements the semantics of Picobot. you'll need to
transform statements from your internal language into calls to the provided
library.

You'll need to understand the interface for the Picobot library (but not its
implementation!). You should take a look at the file
`src/main/scala/piconot/EmptyAPI.scala` for an example use of the library. You
can also look at the library's [auto-generated documentation][API]. The code for
the library itself is in the `lib` directory. Thanks to sbt, you shouldn't need
to do anything special with the library to build and run your code.

#### Building and running your code

You should be able to load your code into ScalaIDE in the usual way (i.e., by
executing `sbt eclipse` in the top-level directory).

You can also run the program on the command line, by typing `sbt run` in the top
-level directory. (Note, if you run the program from the command line, you'll
probably get a warning about a .css file, and the graphics for the buttons will
look a bit different. The program should still work, though.) If you use sbt and
the provided `build.sbt` file to build your code, you should  automatically have
access to the Picobot library, and it will be included on your classpath when
you compile and run.

### Organizing your code
You can and should add any files you need, to implement your language.

### Example programs
Include at least two sample programs: 

  1. `src/main/scala/piconot/Empty.scala`: a program in your internal DSL that 
  can solve the empty maze. A file that describes an empty maze is in 
  `resources/empty.txt`.
  
  2. `src/main/scala/piconot/RightHand.scala`: a program in your internal DSL 
  that uses the right-hand rule to solve the maze in `resources/maze.txt`.

## A running diary As you work, comment on your experience in the file
`evaluation.md`. In particular, each time you change your ideal syntax, you
should describe what changed and why you made the change (e.g., your original
idea required an operator that you couldn't implement in Scala or integrate with
your other ides ). You should also answer the following questions: On a scale of
1–10 (where 10 is "a lot"), how much did you have to change your syntax? On a
scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your
syntax to the provided API?

## Advice

**Give yourself a time budget.** This project can easily eat up hours of time,
as you try to get your language _just right_. If you've got that kind of time --
go for it! But if your time is limited, a budget would be good. Here's a
suggestion, though feel free to alter it:

   + 15 minutes reviewing Picobot
   + 1 hour coming up with a new syntax design
   + 30 minutes writing the example(s) in your ideal syntax
   + 4-5 hours implementing your design
   + 1 hour answering the questions in `design.md` and `evaluation.md`
   + 45 minutes: critique / peer review

Note that this doesn't leave _that_ much time for implementing your design. You
might have to make hard choices and radically change your syntax to get things
working. That's okay! You can always come back to it when you have more time.

## Peer-review another team's work
Comment on another team's design and implementation. You should look through
their grammars, pay special attention to their `design.md` file and look over
their implementation. You might consider the following questions:

  - What are your responses to their original grammar design? Is it designed
  appropriately for its target audience. What do you like about it? Do you have 
  any improvements to suggest? 
  - What good insights about implementation did the team in `design.md`? Did
  you have any experiences that were similar to the team?
  - Are there any implementation tricks that you can suggest to the team?
  Anything you see that might make the implementation easier or more like the
  original design?

**Each member of your team should comment on another team's work**. In other 
words, treat the critique as if you were doing it by yourself (although you can
definitely work side-by-side with your partner(s), if you'd like).
