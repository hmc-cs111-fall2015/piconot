# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

This design is for CS5 students. We assume that students have a basic understanding of scope and syntax as well as reading the picobot assignment. At the very least, someone should be able to read a program and understand what steps picobot should take.

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

We chose this design because they seemed like natural improvements over the original picobot syntax. We think it would be better for maze-searching computation using this syntax because it is easier to read and debug. Also, many of the repeated statements can be removed (i.e. the rules are grouped by state  instead of restated on each rule).

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

It is easier in our design to write complex rules because of the better readability.
Also, we've designed a way to give orderings to rules (if-elseif-else style) which may allow users to write more interesting behaviors with fewer states. 

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

We designed our DSL to span all the behaviors of picobot, being careful to allow all behaviors that picobot could originally do. So, I don't believe our DSL makes anything impossible to express. 
Creating simple picobot programs that are one or two states with one or two rules is probably shorter in the original picobot language in terms of shear number of characters.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

8-9, really our DSL is syntactic sugar with some additional functionality.
Programs _look_ very different from Picobot structurally but we kept some of the vocabulary such as North/East/West/South so it isn't a complete overhaul.

## Is there anything you don’t like about your design?

It is a "boring" design and isn't designed for pirates, though it captures exciting ideas about readability and useability in language design.
