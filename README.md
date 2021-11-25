# Build & Run

I chose to use Java-16 language features (records), so you will at least need that plus maven to build it:

    mvn package
    
# Remarks

## Allocation free programming

The job description mentions "allocation free programming". As the assignment didn't mention that, I opted to not go that route in favor of expressive value objects. I am aware that every "new" is costly and an alternative would be using primitives on the stack only. But Valhalla is looming around the corner anyways.

## Graph model

I chose to use linear algebra and graph theory for the problems. That's why the graph is internally stored as a matrix. Another solution would be a pointer based structure (nodes with edges), that is certainly more intuitive, but then there's  a vectorization opportunity lost, i.e. linear algebra can potentially run on the GPU. 

## Linear Algebra

And then a word why I chose to have Linear Algebra as interfaces. When I started it wasn't clear to me if I have to run my own implementation or can use existing libraries, that's why I started out with interfaces so that I can make the decision later on, once my Email was answered. Also I decided not to use generics here, but rather cast in the concrete implementation. That way I don't have to sprinkle everywhere the concrete LinearAlgebra class as a generic (in every class that would've depend on LinearAlgebra or Matrix).

## jGraphT

Me starting without waiting for an answer to my question is also the reason why I opted now not to use jGraphT. I was already too invested in that implementation once the answer reached me. I would in any case prefer using an existing battle proven solution, but now that I'm so far without jgrapht, I decided to continue now that route, as learning that library would cost me now more effort than finishing the home backed approach. I am totally aware, that this will lead to a far inferior solution.

## Documentation

Ja, I didn't do that here. I'm aware that this is missing.
