# Design

## Who is the target for this design, e.g., are you assuming any knowledge on the part of the language users?

We're still aiming this new language at CS5 students.
We expect that they understand the picobot model and are thinking of it in
terms of a state machine.
Our syntax is also unique - we're not trying to mimic any other language
(including natural language)

## Why did you choose this design, i.e., why did you think it would be a good idea for users to express the maze-searching computation using this syntax?

Our goal with our design was to streamline the language. When writing code the
user should feel like they're specifying the rules for a state machine in the
most expressive way possible.

## What behaviors are easier to express in your design than in Picobot’s original design?  If there are no such behaviors, why not?

Writing rules in our language should feel a lot more natural.
It should feel possible to express your intent through the code.

For example, our picobot uses named states, so that users can indicate the
purpose of each state and refer to states using that purpose.

It also features commutative direction specifications. Users can write `+N +E`
or `+E +E` and they are treated as equivalent.

## What behaviors are more difficult to express in your design than in Picobot’s original design? If there are no such behaviors, why not?

We expect programs will be slightly longer.

Each state will also have additional boilerplate not present in the original
language.

## On a scale of 1–10 (where 10 is “very different”), how different is your syntax from PicoBot’s original design?

3 - It is very similar.

## Is there anything you don’t like about your design?

I think we're going to end up with more boilerplate than we want.

```
mystate = state { ... }
```

is not as nice as

```
mystate { ... }
```
