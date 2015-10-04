# Design

A simple but bad program for the empty room would be as follows:

    action go right
    can move right {
        move right
        go right
    }
    action go up
    can move up {
        move up
        go up
    }

    action go left
    can move left {
        move left
        go left
    }
    action go right 2
    can move right {
        move right
        go right 2
    }
    move down
    go left

Here is a program using abstractions:

    action move all the way (dir) {
        action continue
        can move dir {
            move dir
            continue
        }
    }
    control loop (body) {
        action continue
        body
        continue
    }
    
    move all the way right
    move all the way up
    loop {
        move all the way left
        move all the way right
        move down
    }
    
The basic commands are "can", "can't", "move", "action", and "control". Action defines functions.
In particular, action with just an identifier defines a function which can be used as a "goto".
If an action does take in an argument, it is a direction.
Control defines a control flow structure, which takes in a body.
There's probably a nicer way to combine action and control, but it might require type annotation.

States are simply implicitly created in between lines basic lines (like "move right"),
so this is fairly state-wasteful in the code it generates.

A final trick is that you can also use where the picobot is facing. For example, for a maze:

    move all the way to your right
    loop {
        can move to your right {
            face to your right
        }
        move forward
    }

Since the direction picobot is facing has to be included in the state info, using this can
cause and additional x4 blowup in produced number of states.

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

The target is essentially programmers (or learners), because the thought processes that go into
using the language, picobot in general, and especially defining new commands in the language are targeted
at programmers. However, it is designed so that it might be readable by non-programmers.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

We wanted to capture the essence of what someone is actually thinking when they try to write a
picobot program. It has a lot to do with general, higher-level actions,
and also about ideas like "which direction is Picobot facing?"
And state is usually more of just "what is being done right now", so it made some sense
to go for a actions-go-line-by-line, control-flow-by-goto, BASIC-esque vibe.
Other than that, we wanted it to be extensible.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It is much easier to express a general set of simple actions, more like you might actually want
to tell a Picobot to do directly.  It is also easier to extend the language to become more expressive.
It is also much easier to read at a glance!

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

It is far more difficult to describe some random or arbitrary set of rules. That is,
it would be quite difficult to copy over a Picobot program that you didn't understand.
That's because the whole concept of environment and especially state is somewhat abstracted
away. You would have to use a bunch of statements like

    action state0
    can move right, can't move left { move right; state0 }
    ...

It's also virtually impossible to reduce the number of states being used,
as unless the implementation was particularly clever, even that format would
use many additional states.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

We think 9.

## Is there anything you don’t like about your design?

This particular design is extremely ambiguous in grammar, and difficult to do arbitrary
programming techniques.  Thus, at the expense of making it more reliant on Scala and
programmer knowledge in general, it would be both better and maybe even implementable (!)
to use things like real function definitions and type annotations.
