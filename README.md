# Build & Run

I chose to use Java-16 language features (records), so you will at least need that plus maven to build it:

    mvn package
    
# Remarks

The job description mentions "allocation free programming". As the assignment didn't mention that, I opted to not go that route in favor of expressive value objects. I am aware that every "new" is costly and an alternative would be using primitives on the stack only. But Valhalla is looming around the corner anyways.

I chose to use linear algebra and graph theory for the problems. That's why the graph is internally stored as a matrix. Another solution would be a pointer based structure (nodes with edges), that is certainly more intuitive, but then there's  a vectorization opportunity lost, i.e. linear algebra can potentially run on the GPU. TODO: Disclaimer about own linear algebra vs. library.