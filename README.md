# CladosCalculator
This is the desktop calculator that enables direct computation with the geometric objects in Clados.

# What the Calculator IS
The Clados Calculator started as a way to exercise the functionality of the clados library, so its initial layout can be thought of as a collection of unit tests. Along the way, though, it also became the tool that facilitated feature expansion in clados. The addition of multi-algebra objects (multi-monads or multi-multi-vectors as nyads) showed up between version 0.5 and the present version. Some of Nyad's features were unclear in terms of requirements, so clados and Clados Calculator are progressing in parallel.
<br>
The Calculator is essentially a four-function calculator for geometric objects as expressed by clados. The geometry is expressed as Clifford algebras with a twist. The number 'field' used to represent geometric magnitude has some of the same structure as the geometry, so a single algebra using complex numbers can be written as an order two nyad. Because of this, the soft typing scheme used for monads in cladosG is repeated for numbers in cladosF and that impacts how numeric operations work in the calculator.
<br>
Hard object types are largely respected for geometry by the calculator. Very little casting occurs. When it does it involves conversion between java primitives in support of geometry constructors and use of the cladosF.DivField parent to reprent the 'boxed' numbers . The calculator currently supports real and complex numbers expressed as single (float) and double (double) precision floating point decimals. One could construct an order two nyad like a complex monad, but it isn't necessary anymore. Nyads don't contain nyads, so this approach provides better support in debugging applications using complex numbers in field theories.
<br>
Soft object types (tags) are in use to represent reference frames at run-time when it is far too late to create new static types. For example, two monads referencing tangent points for their coordinate basis sets both use Foot references, but must be able to distinguish between the feet to protect against improper comparisons and algebraic operations. Having a Foot is not enough, though. Which foot is referenced by a monad matters. Distinct objects are enough to ensure comparisons and operations are valid, but the 'boxed' strings in each Foot ensures a human reader can identify them. It's like naming points on a Euclidean plane. All labels are points, but might be distinct points.
<br>
A pure four-function calculator has arithmetic operations acting on pure 'scalar' magnitudes. CladosF is the package intended for division fields commonly used for pure magnitudes. Real and Complex numbers can be imitated with zero and one generator geometric algebras, but there isn't much point in doing so if the user does not intend to assign geometric meaning to those generators. If one sticks to division algebras for magnitudes while avoiding giving meaning to any generators, one might as well use division fields.
<br>
The basic four functions are the two familiar operations of multiplication and addition and their inverses. They mean more in a geometry calculator, though, because the objects being operated upon typically have geometric meaning. Only in small division algebras do the old meanings we learned for numbers in elementary school apply.


# What the Calculator IS NOT
The calculator does not support a number of functions that are already better handled elsewhere by other better applications. Remember that clados is inteded as a library for use by other developrs of physical models. It is NOT intended to compete with teaching, presentation, and scripting environments.

## Graphical Representations<br>
Graphical representations of geometry described by multivectors are relatively straightforward for algebras with a small number of generators.  However, the development work quickly escalates for higher algebras and mixed signatures.  Development of such a feature would turn the application from a calculator to a presenter because the bulk of the code would be for drawing the geometry. Others may tackle such an effort if they wish for higher algebras, but we won't here.  There are already good tools available for the simpler cases. Consider Wolfram's platform.<br>
This constraint might fall by the wayside (only a bit) in order to provide a better UI for input of data. For example, input of complex numbers might involve a polar representation combined with pinch, spread, and pick motions to set field values on the Field Bar.<br>

## Scripted Calculations<br>
Scripted calculations involve development of a suitable programming or macro language. Since part of the purpose of the calculator is to act as a test platform for the Clados library, there is a conflict of purpose here. Besides, there are already suitable scripting environments in other tools, so it would make more sense to enable Clados to be plugins in those tools and re-use their well-developed and tested platforms.<br>
This constraint might fall by the wayside as the cladosPhysics package gets underway. It could make sense to form a basic language around the use of geometry in Physics. For example, one can build factory objects and methods that would constrain application geometry to enforce Relativity. Doing so might best be done with a simple langauge that ensured grammatic and semantic order within a physical model. <br>

## Type∫etting ╒unctions<br>
Well developed presentations and print-ready layouts of objects and results for use in papers and publications involves suitable integration with adapters for external tools already in use by the community. This author has little interest in such things, but is open to any work others might contribute in these directions. One exception to this is the author's intent to develop a simple save and load routine so current objects in the stack may be stored and reloaded at a later time. The XML and JSON methods in the code today are a partial step in this direction.

# Want Detail?

Well... There are basic object manipulators and something like the basic four functions one expects from a calculator application. The thing is... geometry is more complicated. Objects have magnitude and orientation.

Check out the Readme.pdf file for more high level descriptions, but prepare yourself for a detailed dive into examples before you except to comprehend how geometry is being represented. A book of examples exists, but it is rather old now and needs updating and more detail. This calculator is actually ONE example of detail that will go into that book.



## Copyright © 2025 Alfred Differ

## org.interworldtransport.*

This code is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. 
<br>
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.<br>
<br> 
Use of this code or executable objects derived from it by the Licensee  states their willingness to accept the terms of the license. <br> 
<br>
You should have received a copy of the GNU Affero General Public License along with this program.  If not, see https://www.gnu.org/licenses/
