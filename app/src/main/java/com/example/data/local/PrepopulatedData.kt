package com.example.data.local

import com.example.data.model.KnowledgeEntryEntity
import com.example.data.model.LanguageEntity

object PrepopulatedData {
    val languages = listOf(
        LanguageEntity(
            id = "python",
            name = "Python",
            slug = "python",
            description = "High-level, interpreted programming language known for readable syntax and rich ecosystem for web, data science, and scripting.",
            officialDocUrl = "https://docs.python.org/3/",
            category = "General",
            colorHex = "#3572A5",
            badgeIconName = "python"
        ),
        LanguageEntity(
            id = "javascript",
            name = "JavaScript",
            slug = "javascript",
            description = "Dynamic, prototype-based language powering client-side web interactions, modern server runtimes (Node.js/Deno), and full-stack development.",
            officialDocUrl = "https://developer.mozilla.org/en-US/docs/Web/JavaScript",
            category = "Web",
            colorHex = "#F7DF1E",
            badgeIconName = "javascript"
        ),
        LanguageEntity(
            id = "typescript",
            name = "TypeScript",
            slug = "typescript",
            description = "Typed superset of JavaScript that compiles to plain JavaScript, providing compile-time type safety, interfaces, and tooling for large applications.",
            officialDocUrl = "https://www.typescriptlang.org/docs/",
            category = "Web",
            colorHex = "#3178C6",
            badgeIconName = "typescript"
        ),
        LanguageEntity(
            id = "kotlin",
            name = "Kotlin",
            slug = "kotlin",
            description = "Modern concise statically-typed language by JetBrains, fully interoperable with Java and the official language for Android development.",
            officialDocUrl = "https://kotlinlang.org/docs/home.html",
            category = "General",
            colorHex = "#7F52FF",
            badgeIconName = "kotlin"
        ),
        LanguageEntity(
            id = "rust",
            name = "Rust",
            slug = "rust",
            description = "Systems programming language focused on memory safety, zero-cost abstractions, and thread concurrency without a garbage collector.",
            officialDocUrl = "https://doc.rust-lang.org/book/",
            category = "Systems",
            colorHex = "#DEA584",
            badgeIconName = "rust"
        ),
        LanguageEntity(
            id = "go",
            name = "Go",
            slug = "go",
            description = "Statically typed, compiled language engineered at Google featuring lightweight goroutines, channels, fast compilation, and high concurrency.",
            officialDocUrl = "https://go.dev/doc/",
            category = "Systems",
            colorHex = "#00ADD8",
            badgeIconName = "go"
        ),
        LanguageEntity(
            id = "java",
            name = "Java",
            slug = "java",
            description = "Object-oriented, class-based, multiplatform language running on the JVM, widely used in enterprise backend systems and microservices.",
            officialDocUrl = "https://docs.oracle.com/en/java/",
            category = "General",
            colorHex = "#B07219",
            badgeIconName = "java"
        ),
        LanguageEntity(
            id = "cplusplus",
            name = "C++",
            slug = "cplusplus",
            description = "High-performance compiled language providing low-level memory manipulation, object-oriented, functional, and generic programming paradigms.",
            officialDocUrl = "https://en.cppreference.com/w/",
            category = "Systems",
            colorHex = "#F34B7D",
            badgeIconName = "cpp"
        ),
        LanguageEntity(
            id = "c",
            name = "C",
            slug = "c",
            description = "Foundational procedural language providing direct memory access, pointer manipulation, and maximum execution efficiency for OS kernels and drivers.",
            officialDocUrl = "https://en.cppreference.com/w/c",
            category = "Systems",
            colorHex = "#555555",
            badgeIconName = "c"
        ),
        LanguageEntity(
            id = "csharp",
            name = "C#",
            slug = "csharp",
            description = "Modern, type-safe, object-oriented language developed by Microsoft for .NET applications, cloud services, and Unity game development.",
            officialDocUrl = "https://learn.microsoft.com/en-us/dotnet/csharp/",
            category = "General",
            colorHex = "#178600",
            badgeIconName = "csharp"
        ),
        LanguageEntity(
            id = "sql",
            name = "SQL",
            slug = "sql",
            description = "Declarative query language standard for storing, manipulating, and retrieving relational data across databases like PostgreSQL, MySQL, and SQLite.",
            officialDocUrl = "https://www.postgresql.org/docs/",
            category = "Data",
            colorHex = "#E38C00",
            badgeIconName = "sql"
        ),
        LanguageEntity(
            id = "php",
            name = "PHP",
            slug = "php",
            description = "Server-side scripting language designed for web development, powering modern frameworks like Laravel and majority of content management systems.",
            officialDocUrl = "https://www.php.net/manual/en/",
            category = "Web",
            colorHex = "#4F5D95",
            badgeIconName = "php"
        ),
        LanguageEntity(
            id = "ruby",
            name = "Ruby",
            slug = "ruby",
            description = "Dynamic, open-source programming language with a focus on developer happiness and productivity, made famous by the Ruby on Rails framework.",
            officialDocUrl = "https://www.ruby-lang.org/en/documentation/",
            category = "Web",
            colorHex = "#701516",
            badgeIconName = "ruby"
        ),
        LanguageEntity(
            id = "swift",
            name = "Swift",
            slug = "swift",
            description = "Powerful, intuitive programming language created by Apple for iOS, macOS, watchOS, and tvOS with strict safety and modern syntax.",
            officialDocUrl = "https://www.swift.org/documentation/",
            category = "General",
            colorHex = "#F05138",
            badgeIconName = "swift"
        ),
        LanguageEntity(
            id = "html",
            name = "HTML",
            slug = "html",
            description = "HyperText Markup Language that forms the structural backbone of every webpage, defining headings, paragraphs, links, and multimedia elements.",
            officialDocUrl = "https://developer.mozilla.org/en-US/docs/Web/HTML",
            category = "Web",
            colorHex = "#E34F26",
            badgeIconName = "html"
        ),
        LanguageEntity(
            id = "css",
            name = "CSS",
            slug = "css",
            description = "Cascading Style Sheets describing presentation, layouts, responsive media queries, typography, and animations for HTML elements.",
            officialDocUrl = "https://developer.mozilla.org/en-US/docs/Web/CSS",
            category = "Web",
            colorHex = "#563D7C",
            badgeIconName = "css"
        ),
        LanguageEntity(
            id = "bash",
            name = "Bash",
            slug = "bash",
            description = "Unix shell and command language interpreter for automated server administration, pipeline scripting, and command-line automation.",
            officialDocUrl = "https://www.gnu.org/software/bash/manual/",
            category = "Scripting",
            colorHex = "#89E051",
            badgeIconName = "bash"
        )
    )

    val entries = listOf(
        // 1. Python List Comprehension
        KnowledgeEntryEntity(
            id = "py_list_comprehension",
            title = "List Comprehensions",
            slug = "list-comprehensions-python",
            definition = "A concise, expressive syntax for creating new lists by transforming and filtering an existing iterable in a single line.",
            contentType = "Syntax and operators",
            languageSlug = "python",
            languageName = "Python",
            syntax = "[expression for item in iterable if condition]",
            exampleCode = """# Transform and filter a list of numbers
numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
evens_squared = [n ** 2 for n in numbers if n % 2 == 0]

print(evens_squared)
# Output: [4, 16, 36, 64, 100]

# Nested comprehension for flattening a matrix
matrix = [[1, 2], [3, 4], [5, 6]]
flat = [num for row in matrix for num in row]
print(flat)
# Output: [1, 2, 3, 4, 5, 6]""",
            exampleExplanation = "The expression (n ** 2) is evaluated for each item (n) in numbers only if (n % 2 == 0) holds true. This produces cleaner code than traditional for loops with append calls and often runs noticeably faster due to bytecode optimizations.",
            commonMistakes = "Avoid overly nested or deeply complex list comprehensions with more than two for clauses, as they quickly become difficult to read. If side effects or multi-statement logic is needed, use a standard for-loop.",
            difficulty = "Beginner",
            tags = "python, list, comprehension, iterable, loop, functional, array",
            sourceUrl = "https://docs.python.org/3/tutorial/datastructures.html#list-comprehensions",
            sourceName = "Python 3 Official Docs",
            relatedConcepts = "Generator Expressions, Dictionary Comprehensions, map(), filter()",
            isTrending = true,
            popularityScore = 98
        ),

        // 2. JavaScript let vs const
        KnowledgeEntryEntity(
            id = "js_let_vs_const",
            title = "Difference between let and const",
            slug = "difference-between-let-and-const",
            definition = "Both let and const provide block-scoped variable declarations introduced in ES6, but const prevents variable reassignment while let permits it.",
            contentType = "Coding questions",
            languageSlug = "javascript",
            languageName = "JavaScript",
            syntax = "const immutableRef = value;\nlet mutableVar = value;",
            exampleCode = """// const cannot be reassigned:
const maxUsers = 100;
// maxUsers = 150; // TypeError: Assignment to constant variable

// Objects declared with const CAN still mutate internal properties:
const user = { name: "Alice", role: "Dev" };
user.role = "Admin"; // Valid! Object mutation is permitted.
console.log(user.role); // "Admin"

// let allows re-binding the variable identifier:
let currentCount = 0;
currentCount += 1; // Valid!
console.log(currentCount); // 1""",
            exampleExplanation = "Use 'const' by default for all variables to communicate intent and guard against unexpected re-binding. Only use 'let' when you know the identifier must re-point to a new value (like loop counters). Neither variable gets hoisted to global scope without the temporal dead zone (TDZ).",
            commonMistakes = "Thinking const makes objects deeply immutable. 'const' only freezes the variable reference, not the underlying object data. Use Object.freeze() if you need shallow immutability.",
            difficulty = "Beginner",
            tags = "javascript, es6, let, const, scope, variables, tdz",
            sourceUrl = "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/const",
            sourceName = "MDN Web Docs",
            relatedConcepts = "var vs let, Temporal Dead Zone, Block Scope, Object.freeze()",
            isTrending = true,
            popularityScore = 95
        ),

        // 3. Concept: What is recursion?
        KnowledgeEntryEntity(
            id = "concept_recursion",
            title = "What is recursion?",
            slug = "what-is-recursion",
            definition = "A programming technique where a function solves a problem by calling itself with smaller inputs until reaching a defined base case.",
            contentType = "Concepts",
            languageSlug = null,
            languageName = null,
            syntax = "function recurse(n) {\n  if (baseCondition(n)) return baseValue;\n  return recurse(modifiedN);\n}",
            exampleCode = """// Factorial calculation via recursion
function factorial(n) {
  // Base case: prevents infinite call stack
  if (n <= 1) {
    return 1;
  }
  // Recursive step: n! = n * (n - 1)!
  return n * factorial(n - 1);
}

console.log(factorial(5)); // 120

// Tree Traversal recursion
function sumTree(node) {
  if (!node) return 0;
  return node.value + sumTree(node.left) + sumTree(node.right);
}""",
            exampleExplanation = "Every recursive function must contain two essential parts: (1) One or more Base Cases to stop the recursion, and (2) The Recursive Step that reduces the problem towards the base case. The call stack maintains local states across invocations.",
            commonMistakes = "Forgetting the base case or failing to decrease input bounds, resulting in maximum call stack size exceeded or stack overflow crashes.",
            difficulty = "Beginner",
            tags = "algorithm, recursion, callstack, base-case, divide-and-conquer, data-structures",
            sourceUrl = "https://en.wikipedia.org/wiki/Recursion_(computer_science)",
            sourceName = "Computer Science Fundamentals",
            relatedConcepts = "Tail Call Optimization, Iteration vs Recursion, Stack Overflow, Divide and Conquer",
            isTrending = true,
            popularityScore = 99
        ),

        // 4. JavaScript Async/Await
        KnowledgeEntryEntity(
            id = "js_async_await",
            title = "How does async/await work?",
            slug = "how-does-async-await-work",
            definition = "Syntactic sugar built on Promises allowing asynchronous non-blocking code to be written and read sequentially like synchronous code.",
            contentType = "Coding questions",
            languageSlug = "javascript",
            languageName = "JavaScript",
            syntax = "async function fetchData() {\n  const res = await fetch(url);\n  return res.json();\n}",
            exampleCode = """// Fetching remote data with error handling
async function getUserProfile(userId) {
  try {
    const response = await fetch(`https://api.example.com/users/${'$'}{userId}`);
    if (!response.ok) {
      throw new Error(`HTTP Error: ${'$'}{response.status}`);
    }
    const profile = await response.json();
    return profile;
  } catch (err) {
    console.error("Failed to load user:", err.message);
    throw err;
  }
}

// Parallel execution with Promise.all
async function loadDashboard() {
  const [stats, alerts] = await Promise.all([
    fetchStats(),
    fetchAlerts()
  ]);
  return { stats, alerts };
}""",
            exampleExplanation = "An 'async' function always wraps return values in a Promise. The 'await' expression pauses the async function execution without blocking the main event thread, resuming once the Promise settles.",
            commonMistakes = "Awaiting independent operations in serial inside a loop instead of launching them concurrently with Promise.all(). Forgetting try/catch blocks leads to unhandled promise rejections.",
            difficulty = "Intermediate",
            tags = "javascript, async, await, promises, eventloop, fetch, concurrency",
            sourceUrl = "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function",
            sourceName = "MDN Web Docs",
            relatedConcepts = "Promises, Event Loop, Microtasks, Promise.all()",
            isTrending = true,
            popularityScore = 97
        ),

        // 5. Python Reverse String
        KnowledgeEntryEntity(
            id = "py_reverse_string",
            title = "How do I reverse a string in Python?",
            slug = "how-to-reverse-string-python",
            definition = "The most Pythonic and efficient way to reverse a string in Python is using extended slice syntax [::-1] or reversed() with join().",
            contentType = "Coding questions",
            languageSlug = "python",
            languageName = "Python",
            syntax = "reversed_text = original_text[::-1]",
            exampleCode = """original = "CodeAtlas Universal Engine"

# 1. Extended Slice (Most idiomatic & fast)
reversed_slice = original[::-1]
print(reversed_slice)
# Output: "enignE lasrevinU saltAedoC"

# 2. Using reversed() and str.join()
reversed_iter = "".join(reversed(original))
print(reversed_iter)

# Check Palindrome with reverse slice
def is_palindrome(word: str) -> bool:
    clean = word.lower().replace(" ", "")
    return clean == clean[::-1]

print(is_palindrome("racecar")) # True""",
            exampleExplanation = "Slice notation follows [start:stop:step]. When step is -1 and start/stop are omitted, Python slices from the end to the beginning in reverse order in optimized C code.",
            commonMistakes = "Calling .reverse() on a string. Strings in Python are immutable, so they do not have an in-place .reverse() method like lists do.",
            difficulty = "Beginner",
            tags = "python, string, slice, reverse, palindrome, syntax",
            sourceUrl = "https://docs.python.org/3/whatsnew/2.3.html#extended-slices",
            sourceName = "Python Documentation",
            relatedConcepts = "String Immutability, Slice Notation, reversed() Built-in",
            isTrending = false,
            popularityScore = 90
        ),

        // 6. Python TypeError
        KnowledgeEntryEntity(
            id = "py_type_error",
            title = "Why am I getting a TypeError?",
            slug = "why-am-i-getting-typeerror-python",
            definition = "A TypeError occurs when an operation or function is applied to an object of an inappropriate or unsupported data type.",
            contentType = "Errors and debugging",
            languageSlug = "python",
            languageName = "Python",
            syntax = "TypeError: unsupported operand type(s) for +: 'int' and 'str'",
            exampleCode = """# Common cause 1: Concatenating string with integer
# age = 25
# print("Age: " + age) # TypeError!

# Fix 1: Use f-strings or str() conversion
age = 25
print(f"Age: {age}") # "Age: 25"

# Common cause 2: Subscripting None
def get_user_data(user_id):
    if user_id <= 0:
        return None
    return {"name": "Zane"}

result = get_user_data(-1)
# name = result["name"] # TypeError: 'NoneType' object is not subscriptable!

# Fix 2: Check for None or use safe access
if result is not None:
    print(result["name"])
else:
    print("User not found")""",
            exampleExplanation = "Python is strongly typed and will never implicitly coerce numbers to strings in addition. Inspect traceback line numbers and use type annotations or isinstance() assertions during debugging.",
            commonMistakes = "Assuming functions always return dictionaries or lists without checking if they returned None on error or early return.",
            difficulty = "Beginner",
            tags = "python, error, typeerror, debugging, nonetype, exceptions",
            sourceUrl = "https://docs.python.org/3/library/exceptions.html#TypeError",
            sourceName = "Python Standard Exceptions",
            relatedConcepts = "Exception Handling, Tracebacks, Type Hints, NoneType",
            isTrending = true,
            popularityScore = 92
        ),

        // 7. SQL Joins
        KnowledgeEntryEntity(
            id = "sql_joins",
            title = "How do SQL joins work?",
            slug = "how-do-sql-joins-work",
            definition = "SQL JOIN clauses combine columns from one or more relational tables based on a related common key between them.",
            contentType = "Coding questions",
            languageSlug = "sql",
            languageName = "SQL",
            syntax = "SELECT columns\nFROM table_a\n[INNER | LEFT | RIGHT | FULL] JOIN table_b\n  ON table_a.key = table_b.key;",
            exampleCode = """-- 1. INNER JOIN: Rows matching in BOTH tables
SELECT u.name, o.order_id, o.total_amount
FROM users u
INNER JOIN orders o ON u.id = o.user_id;

-- 2. LEFT JOIN: ALL rows from users, matched orders (or NULL)
SELECT u.name, COUNT(o.order_id) AS total_orders
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id, u.name;

-- 3. Find users who have NEVER placed an order
SELECT u.name, u.email
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE o.order_id IS NULL;""",
            exampleExplanation = "INNER JOIN only retains records where the join predicate matches. LEFT JOIN retains all records from the left table and inserts NULLs for unmatched columns from the right table.",
            commonMistakes = "Filtering right-table columns in the WHERE clause instead of the ON clause during a LEFT JOIN, which accidentally converts it into an INNER JOIN by filtering out NULL values.",
            difficulty = "Beginner",
            tags = "sql, database, join, inner-join, left-join, relational, postgresql",
            sourceUrl = "https://www.postgresql.org/docs/current/queries-table-expressions.html#QUERIES-FROM",
            sourceName = "PostgreSQL Official Documentation",
            relatedConcepts = "Primary & Foreign Keys, Relational Normalization, Indexing",
            isTrending = true,
            popularityScore = 94
        ),

        // 8. Kotlin Coroutines & Suspend
        KnowledgeEntryEntity(
            id = "kt_coroutines",
            title = "Kotlin Coroutines & Suspend Functions",
            slug = "kotlin-coroutines-suspend-functions",
            definition = "Lightweight threads that can be paused and resumed without blocking the underlying OS thread, enabling scalable asynchronous programming.",
            contentType = "Concepts",
            languageSlug = "kotlin",
            languageName = "Kotlin",
            syntax = "suspend fun doWork(): Result = withContext(Dispatchers.IO) { ... }",
            exampleCode = """class UserRepository(private val api: ApiService) {
    // Suspend function pauses without blocking UI thread
    suspend fun fetchUserProfile(userId: String): UserProfile =
        withContext(Dispatchers.IO) {
            val response = api.getUser(userId)
            response.toDomainModel()
        }
}

// In ViewModel:
viewModelScope.launch {
    try {
        val user = repository.fetchUserProfile("usr_99")
        _uiState.update { it.copy(user = user, isLoading = false) }
    } catch (e: Exception) {
        _uiState.update { it.copy(errorMessage = e.message) }
    }
}""",
            exampleExplanation = "Kotlin transforms suspend functions into state machines behind the scenes. When a suspension point is reached (like network I/O), the thread is released to do other work until data arrives.",
            commonMistakes = "Calling blocking calls like Thread.sleep() instead of delay(), or executing heavy computation on Dispatchers.Main which causes UI freezes (ANRs).",
            difficulty = "Intermediate",
            tags = "kotlin, coroutines, suspend, android, concurrency, flow, dispatchers",
            sourceUrl = "https://kotlinlang.org/docs/coroutines-overview.html",
            sourceName = "Kotlin Documentation",
            relatedConcepts = "StateFlow & SharedFlow, Structured Concurrency, Dispatchers",
            isTrending = true,
            popularityScore = 96
        ),

        // 9. Rust Ownership & Borrowing
        KnowledgeEntryEntity(
            id = "rust_ownership",
            title = "Rust Ownership and Borrowing",
            slug = "rust-ownership-and-borrowing",
            definition = "Rust's compile-time memory management system where every value has a unique owner, and references can be borrowed immutably or mutably without data races.",
            contentType = "Concepts",
            languageSlug = "rust",
            languageName = "Rust",
            syntax = "fn borrow(val: &String) { ... }\nfn borrow_mut(val: &mut String) { ... }",
            exampleCode = """fn main() {
    // 1. Ownership transfer (Move)
    let s1 = String::from("hello atlas");
    let s2 = s1; // Ownership MOVED to s2. s1 is no longer valid!
    // println!("{}", s1); // Compile Error: value borrowed here after move

    // 2. Immutable Borrowing (&T)
    let len = calculate_length(&s2);
    println!("Length of '{}' is {}", s2, len);

    // 3. Mutable Borrowing (&mut T)
    let mut msg = String::from("hello");
    append_world(&mut msg);
    println!("{}", msg); // "hello, world!"
}

fn calculate_length(s: &String) -> usize {
    s.len()
}

fn append_world(s: &mut String) {
    s.push_str(", world!");
}""",
            exampleExplanation = "Rust enforces two borrowing rules: (1) You can have any number of immutable references (&T), OR (2) Exactly one mutable reference (&mut T) at a time, but never both. This eliminates dangling pointers and data races at compile time.",
            commonMistakes = "Trying to retain a reference while simultaneously creating a mutable reference to the same structure in the same scope.",
            difficulty = "Advanced",
            tags = "rust, ownership, borrowing, memory-safety, lifetimes, systems",
            sourceUrl = "https://doc.rust-lang.org/book/ch04-00-understanding-ownership.html",
            sourceName = "The Rust Programming Language",
            relatedConcepts = "Lifetimes, Move Semantics, Smart Pointers, RAII",
            isTrending = true,
            popularityScore = 97
        ),

        // 10. Go Goroutines and Channels
        KnowledgeEntryEntity(
            id = "go_goroutines",
            title = "Go Goroutines and Channels",
            slug = "go-goroutines-and-channels",
            definition = "Extremely lightweight user-space threads managed by Go's runtime, communicating safely using typed conduits called channels.",
            contentType = "Concepts",
            languageSlug = "go",
            languageName = "Go",
            syntax = "go workerFunction()\nch := make(chan string)\nch <- message\nmsg := <-ch",
            exampleCode = """package main

import (
	"fmt"
	"time"
)

func worker(id int, jobs <-chan int, results chan<- int) {
	for j := range jobs {
		fmt.Printf("Worker %d processing job %d\n", id, j)
		time.Sleep(time.Millisecond * 100)
		results <- j * 2
	}
}

func main() {
	jobs := make(chan int, 5)
	results := make(chan int, 5)

	// Spawn 3 concurrent workers
	for w := 1; w <= 3; w++ {
		go worker(w, jobs, results)
	}

	for j := 1; j <= 5; j++ {
		jobs <- j
	}
	close(jobs)

	for a := 1; a <= 5; a++ {
		fmt.Printf("Result: %d\n", <-results)
	}
}""",
            exampleExplanation = "Go's philosophy is: 'Do not communicate by sharing memory; instead, share memory by communicating.' Goroutines take only a few kilobytes of stack memory and scale to tens of thousands per process.",
            commonMistakes = "Forgetting to close channels leading to deadlocks in range loops, or writing to an uninitialized nil channel which blocks forever.",
            difficulty = "Intermediate",
            tags = "go, golang, goroutines, channels, concurrency, csp, select",
            sourceUrl = "https://go.dev/tour/concurrency/1",
            sourceName = "A Tour of Go",
            relatedConcepts = "Buffered Channels, select statement, sync.WaitGroup, sync.Mutex",
            isTrending = true,
            popularityScore = 93
        ),

        // 11. TypeScript Generics
        KnowledgeEntryEntity(
            id = "ts_generics",
            title = "TypeScript Generics",
            slug = "typescript-generics",
            definition = "A tool for creating reusable components that can work over a variety of types rather than a single one, preserving type safety.",
            contentType = "Syntax and operators",
            languageSlug = "typescript",
            languageName = "TypeScript",
            syntax = "function identity<T>(arg: T): T { return arg; }",
            exampleCode = """// Generic API Response wrapper
interface ApiResponse<TData> {
  status: "success" | "error";
  data: TData;
  timestamp: number;
}

interface User {
  id: string;
  name: string;
  email: string;
}

// Function constrained to objects with an id property
function getEntityId<T extends { id: string }>(item: T): string {
  return item.id;
}

const userRes: ApiResponse<User> = {
  status: "success",
  data: { id: "u12", name: "Sara", email: "sara@code.dev" },
  timestamp: Date.now()
};

console.log(getEntityId(userRes.data)); // "u12"""",
            exampleExplanation = "Generics allow developers to capture the argument type (T) and reuse it across return types, internal variables, and interfaces without falling back to 'any'.",
            commonMistakes = "Overusing generics where a union type or simple interface would suffice, or using 'any' which completely disables compiler type checks.",
            difficulty = "Intermediate",
            tags = "typescript, generics, types, interfaces, type-safety, web",
            sourceUrl = "https://www.typescriptlang.org/docs/handbook/2/generics.html",
            sourceName = "TypeScript Handbook",
            relatedConcepts = "Type Constraints, Utility Types, Conditional Types, keyof",
            isTrending = true,
            popularityScore = 91
        ),

        // 12. Binary Search Algorithm
        KnowledgeEntryEntity(
            id = "algo_binary_search",
            title = "Binary Search Algorithm",
            slug = "binary-search-algorithm",
            definition = "An efficient search algorithm that finds the position of a target value within a sorted array in O(log n) logarithmic time by halving search intervals.",
            contentType = "Algorithms and data structures",
            languageSlug = null,
            languageName = null,
            syntax = "while (low <= high) { mid = low + (high - low) / 2; ... }",
            exampleCode = """// Binary Search in Python
def binary_search(arr: list[int], target: int) -> int:
    left, right = 0, len(arr) - 1
    
    while left <= right:
        # Avoid integer overflow with (left + (right - left) // 2)
        mid = left + (right - left) // 2
        
        if arr[mid] == target:
            return mid # Target found at index mid
        elif arr[mid] < target:
            left = mid + 1 # Search right half
        else:
            right = mid - 1 # Search left half
            
    return -1 # Target not present

sorted_list = [2, 5, 8, 12, 16, 23, 38, 56, 72, 91]
print(binary_search(sorted_list, 23)) # 5
print(binary_search(sorted_list, 40)) # -1""",
            exampleExplanation = "Binary search repeatedly compares the target with the median element of the sorted range. If unequal, the half in which the target cannot lie is eliminated, reducing problem size by 50% each iteration.",
            commonMistakes = "Attempting to use binary search on an unsorted array (which produces wrong answers), or calculating mid with (left + right) / 2 which can overflow in languages like Java/C++.",
            difficulty = "Beginner",
            tags = "algorithm, binary-search, searching, logn, divide-and-conquer, array",
            sourceUrl = "https://en.wikipedia.org/wiki/Binary_search_algorithm",
            sourceName = "Algorithm Design Manual",
            relatedConcepts = "Two Pointers, Divide and Conquer, Time Complexity, Array Indexing",
            isTrending = true,
            popularityScore = 96
        ),

        // 13. C++ Smart Pointers
        KnowledgeEntryEntity(
            id = "cpp_smart_pointers",
            title = "C++ Smart Pointers (std::unique_ptr & std::shared_ptr)",
            slug = "cpp-smart-pointers",
            definition = "Template classes that manage dynamically allocated heap memory using RAII, automatically freeing memory when pointers go out of scope.",
            contentType = "Functions and methods",
            languageSlug = "cplusplus",
            languageName = "C++",
            syntax = "auto u = std::make_unique<T>();\nauto s = std::make_shared<T>();",
            exampleCode = """#include <iostream>
#include <memory>

class Resource {
public:
    Resource() { std::cout << "Acquired resource\n"; }
    ~Resource() { std::cout << "Destroyed resource\n"; }
    void run() { std::cout << "Operating...\n"; }
};

int main() {
    // unique_ptr: Sole ownership, zero runtime overhead
    std::unique_ptr<Resource> ptr1 = std::make_unique<Resource>();
    ptr1->run();

    // Ownership can be transferred with std::move
    std::unique_ptr<Resource> ptr2 = std::move(ptr1);
    // ptr1 is now nullptr

    // shared_ptr: Reference-counted shared ownership
    std::shared_ptr<Resource> s1 = std::make_shared<Resource>();
    std::cout << "Ref count: " << s1.use_count() << "\n"; // 1
    {
        std::shared_ptr<Resource> s2 = s1;
        std::cout << "Ref count: " << s1.use_count() << "\n"; // 2
    } // s2 goes out of scope, count decrements
    std::cout << "Ref count: " << s1.use_count() << "\n"; // 1
    return 0;
}""",
            exampleExplanation = "Smart pointers wrap raw pointers to guarantee exception safety and prevent memory leaks. std::unique_ptr cannot be copied, only moved. std::shared_ptr uses thread-safe reference counting.",
            commonMistakes = "Creating circular references with std::shared_ptr without using std::weak_ptr, which leads to memory leaks because reference count never drops to zero.",
            difficulty = "Intermediate",
            tags = "cpp, c++, smart-pointers, raii, memory, unique_ptr, shared_ptr",
            sourceUrl = "https://en.cppreference.com/w/cpp/memory/unique_ptr",
            sourceName = "cppreference.com",
            relatedConcepts = "RAII, Move Semantics, std::weak_ptr, Memory Leaks",
            isTrending = false,
            popularityScore = 88
        ),

        // 14. Bash Piping and Redirection
        KnowledgeEntryEntity(
            id = "bash_pipe_redirect",
            title = "Bash Pipes and I/O Redirection",
            slug = "bash-pipes-and-redirection",
            definition = "Mechanisms to direct output from one program into another as input (|), or redirect streams to and from files (>, >>, 2>&1).",
            contentType = "Syntax and operators",
            languageSlug = "bash",
            languageName = "Bash",
            syntax = "command1 | command2\ncommand > file 2>&1",
            exampleCode = """# 1. Pipe stdout into stdin of another utility
cat access.log | grep "404" | cut -d ' ' -f 7 | sort | uniq -c | sort -nr

# 2. Redirect stdout (overwrite)
echo "Starting job..." > deploy.log

# 3. Redirect stdout and stderr (append)
./run_server.sh >> server.log 2>&1

# 4. Filter and process files in parallel with xargs
find . -name "*.log" | xargs grep -i "error"""",
            exampleExplanation = "The pipe symbol (|) connects stdout of the left command to stdin of the right. The operator 2>&1 merges file descriptor 2 (stderr) into file descriptor 1 (stdout).",
            commonMistakes = "Using > instead of >> when appending to logs, inadvertently wiping the entire file. Neglecting quotes around variables with spaces in bash.",
            difficulty = "Beginner",
            tags = "bash, shell, pipe, redirection, grep, linux, cli",
            sourceUrl = "https://www.gnu.org/software/bash/manual/html_node/Redirections.html",
            sourceName = "GNU Bash Manual",
            relatedConcepts = "File Descriptors, Standard Streams, xargs, awk, sed",
            isTrending = false,
            popularityScore = 85
        ),

        // 15. Java NullPointerException
        KnowledgeEntryEntity(
            id = "java_npe",
            title = "Java NullPointerException (NPE)",
            slug = "java-nullpointerexception-debugging",
            definition = "Runtime exception thrown when an application attempts to use an object reference that points to null.",
            contentType = "Errors and debugging",
            languageSlug = "java",
            languageName = "Java",
            syntax = "java.lang.NullPointerException: Cannot invoke 'String.length()' because 'str' is null",
            exampleCode = """public class UserFinder {
    public static void main(String[] args) {
        String username = getUsernameById(404);
        
        // BUG: username might be null
        // System.out.println(username.toUpperCase()); // Throws NPE!

        // FIX 1: Modern Optional<T>
        Optional<String> optUser = Optional.ofNullable(username);
        String display = optUser.orElse("Guest User");
        System.out.println(display);

        // FIX 2: Modern Java 14+ helpful NPE pinpointing
        // JVM shows exactly which variable evaluated to null!
    }

    public static String getUsernameById(int id) {
        return null; // Simulated not found
    }
}""",
            exampleExplanation = "NPEs happen when calling an instance method on null, accessing a field on null, or taking the length of a null array. Java 14 introduced detailed NPE messages identifying the exact culprit.",
            commonMistakes = "Failing to check return values of database or map lookups before immediately calling methods on them.",
            difficulty = "Beginner",
            tags = "java, error, nullpointerexception, npe, debugging, optional",
            sourceUrl = "https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/NullPointerException.html",
            sourceName = "Oracle Java SE Docs",
            relatedConcepts = "Optional, Null Safety, Defensive Programming, Annotations (@NonNull)",
            isTrending = true,
            popularityScore = 93
        ),

        // 16. CSS Flexbox vs Grid
        KnowledgeEntryEntity(
            id = "css_flexbox_grid",
            title = "CSS Flexbox vs CSS Grid",
            slug = "css-flexbox-vs-css-grid",
            definition = "Flexbox is designed for one-dimensional layouts (a row or a column), whereas CSS Grid is designed for two-dimensional layouts (rows and columns simultaneously).",
            contentType = "Concepts",
            languageSlug = "css",
            languageName = "CSS",
            syntax = "display: flex; justify-content: center;\ndisplay: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));",
            exampleCode = """/* 1. Flexbox: 1D alignment of items inside a navbar */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

/* 2. CSS Grid: 2D responsive cards grid */
.cards-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1.5rem;
}

.card {
  padding: 1.25rem;
  border-radius: 8px;
  background-color: #1e1e2e;
}""",
            exampleExplanation = "Use Flexbox when you want items to flow along a single axis and distribute space organically based on their content size. Use Grid when you want strict alignment across both rows and columns.",
            commonMistakes = "Using complex nested flexbox wrappers to simulate a table or matrix instead of applying CSS Grid.",
            difficulty = "Beginner",
            tags = "css, flexbox, grid, layout, responsive, web, styling",
            sourceUrl = "https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Grid_Layout/Relationship_of_Grid_Layout",
            sourceName = "MDN Web Docs",
            relatedConcepts = "CSS Box Model, Media Queries, align-items, justify-content",
            isTrending = false,
            popularityScore = 87
        ),

        // 17. HTML Semantic Elements
        KnowledgeEntryEntity(
            id = "html_semantic_elements",
            title = "HTML5 Semantic Elements",
            slug = "html5-semantic-elements",
            definition = "Tags that clearly describe their meaning and purpose to both the browser and developer, improving accessibility and SEO.",
            contentType = "Concepts",
            languageSlug = "html",
            languageName = "HTML",
            syntax = "<header>, <nav>, <main>, <article>, <section>, <aside>, <footer>",
            exampleCode = """<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>CodeAtlas Article</title>
</head>
<body>
  <header>
    <h1>CodeAtlas</h1>
    <nav aria-label="Main Navigation">
      <a href="/search">Search</a>
      <a href="/languages">Languages</a>
    </nav>
  </header>

  <main>
    <article>
      <h2>Understanding Async/Await</h2>
      <p>Asynchronous patterns make web apps snappy...</p>
    </article>
    <aside>
      <h3>Related Concepts</h3>
      <ul><li>Promises</li><li>Event Loop</li></ul>
    </aside>
  </main>

  <footer>
    <p>&copy; 2026 CodeAtlas</p>
  </footer>
</body>
</html>""",
            exampleExplanation = "Replacing generic <div> tags with semantic tags (<main>, <article>, <nav>) allows screen readers to provide landmark navigation shortcuts for impaired users and helps search engines index content structure.",
            commonMistakes = "Using <section> purely for styling or wrapping every single div in an article without semantic relevance.",
            difficulty = "Beginner",
            tags = "html, semantic, seo, accessibility, a11y, web",
            sourceUrl = "https://developer.mozilla.org/en-US/docs/Glossary/Semantics#semantics_in_html",
            sourceName = "MDN Web Docs",
            relatedConcepts = "WAI-ARIA, Accessibility, Document Object Model (DOM)",
            isTrending = false,
            popularityScore = 82
        ),

        // 18. Swift Optional Binding
        KnowledgeEntryEntity(
            id = "swift_optional_binding",
            title = "Swift Optional Binding (if let & guard let)",
            slug = "swift-optional-binding",
            definition = "Mechanisms in Swift to safely unwrap Optionals to access wrapped values without causing runtime crashes from nil values.",
            contentType = "Syntax and operators",
            languageSlug = "swift",
            languageName = "Swift",
            syntax = "if let value = optionalValue { ... }\nguard let value = optionalValue else { return }",
            exampleCode = """// 1. guard let: Early exit pattern
func printUserGreeting(name: String?) {
    guard let unwrappedName = name, !unwrappedName.isEmpty else {
        print("Hello, Anonymous Guest!")
        return
    }
    // unwrappedName is in scope for the rest of the function!
    print("Welcome back, \(unwrappedName)!")
}

// 2. if let: Conditional scope
let scoreString: String? = "95"
if let score = scoreString.flatMap(Int.init) {
    print("User achieved: \(score) points")
} else {
    print("Invalid score format")
}""",
            exampleExplanation = "Swift optionals represent either a value or nil. Force unwrapping with '!' can crash if nil. 'guard let' enforces early exits and avoids deep nesting.",
            commonMistakes = "Using force unwrapping (!) in production code, causing fatal error: Unexpectedly found nil while unwrapping an Optional value.",
            difficulty = "Beginner",
            tags = "swift, optional, guard, unwrapping, ios, apple, safety",
            sourceUrl = "https://docs.swift.org/swift-book/documentation/the-swift-programming-language/thebasics/#Optional-Binding",
            sourceName = "The Swift Programming Language",
            relatedConcepts = "Nil-Coalescing Operator, Optional Chaining, Enums",
            isTrending = false,
            popularityScore = 86
        ),

        // 19. PHP Null Coalescing & Arrow Functions
        KnowledgeEntryEntity(
            id = "php_null_coalescing",
            title = "PHP Null Coalescing Operator (??)",
            slug = "php-null-coalescing-operator",
            definition = "A concise ternary shorthand that returns its first operand if it exists and is not null; otherwise returns its second operand.",
            contentType = "Syntax and operators",
            languageSlug = "php",
            languageName = "PHP",
            syntax = "${'$'}value = ${'$'}input ?? 'default_fallback';",
            exampleCode = """<?php
// Traditional isset ternary:
// ${'$'}role = isset(${'$'}_GET['role']) ? ${'$'}_GET['role'] : 'guest';

// Clean Null Coalescing:
${'$'}role = ${'$'}_GET['role'] ?? 'guest';

// Chained Coalescing:
${'$'}theme = ${'$'}userTheme ?? ${'$'}systemTheme ?? 'dark';

// Null coalescing assignment (PHP 7.4+)
${'$'}config['cache_ttl'] ??= 3600;

echo "Selected role: " . ${'$'}role;
?>""",
            exampleExplanation = "Unlike empty() or ternary ?:, ?? does not emit an E_NOTICE warning when accessing undefined array keys or missing object properties.",
            commonMistakes = "Confusing ?? with ?: (Elvis operator). The Elvis operator evaluates falsy values (like 0 or false) as the right-hand side, whereas ?? only evaluates null.",
            difficulty = "Beginner",
            tags = "php, null-coalescing, operators, modern-php, web",
            sourceUrl = "https://www.php.net/manual/en/language.operators.comparison.php#language.operators.comparison.coalesce",
            sourceName = "PHP Manual",
            relatedConcepts = "Ternary Operator, Type Juggling, PHP 8 Modern Features",
            isTrending = false,
            popularityScore = 81
        ),

        // 20. Ruby Blocks, Procs & Yield
        KnowledgeEntryEntity(
            id = "ruby_blocks_yield",
            title = "Ruby Blocks and the yield Keyword",
            slug = "ruby-blocks-and-yield",
            definition = "A chunk of code enclosed in do..end or {} passed into method invocations, executed dynamically with the yield keyword.",
            contentType = "Keywords",
            languageSlug = "ruby",
            languageName = "Ruby",
            syntax = "def my_method\n  yield if block_given?\nend",
            exampleCode = """# Method receiving an implicit block
def measure_execution_time(label)
  start_time = Time.now
  yield if block_given?
  elapsed = Time.now - start_time
  puts "[#{label}] Finished in #{elapsed.round(4)}s"
end

# Invoking with a block:
measure_execution_time("Database Indexing") do
  sleep(0.2)
  puts "Building B-Tree index..."
end

# Enumerable with blocks
numbers = [1, 2, 3, 4]
squared = numbers.map { |n| n ** 2 }
puts squared.inspect # [1, 4, 9, 16]""",
            exampleExplanation = "Blocks are one of Ruby's hallmark features, allowing expressive domain-specific languages (DSLs) like RSpec and Active Record.",
            commonMistakes = "Calling yield when no block was supplied, triggering LocalJumpError: no block given (yield). Always guard with block_given?.",
            difficulty = "Intermediate",
            tags = "ruby, blocks, yield, procs, lambdas, metaprogramming",
            sourceUrl = "https://ruby-doc.org/core-3.1.0/doc/syntax/calling_methods_rdoc.html",
            sourceName = "Ruby Core Documentation",
            relatedConcepts = "Lambdas, Procs, Higher-Order Functions, Enumerable",
            isTrending = false,
            popularityScore = 80
        ),

        // 21. C Pointers & Memory Management
        KnowledgeEntryEntity(
            id = "c_pointers_malloc",
            title = "C Pointers and Dynamic Memory Allocation",
            slug = "c-pointers-and-memory-allocation",
            definition = "Direct address manipulation using pointer variables and allocating heap memory via malloc() and deallocating with free().",
            contentType = "Concepts",
            languageSlug = "c",
            languageName = "C",
            syntax = "int *ptr = (int *)malloc(size * sizeof(int));\nfree(ptr);\nptr = NULL;",
            exampleCode = """#include <stdio.h>
#include <stdlib.h>

int main() {
    int n = 5;
    // Allocate heap memory for 5 integers
    int *arr = (int *)malloc(n * sizeof(int));
    if (arr == NULL) {
        fprintf(stderr, "Memory allocation failed!\n");
        return 1;
    }

    for (int i = 0; i < n; i++) {
        *(arr + i) = (i + 1) * 10; // Pointer arithmetic
    }

    for (int i = 0; i < n; i++) {
        printf("arr[%d] = %d\n", i, arr[i]);
    }

    // Always release allocated heap memory
    free(arr);
    arr = NULL; // Prevent dangling pointer
    return 0;
}""",
            exampleExplanation = "Pointers store raw virtual memory addresses. malloc() reserves contiguous bytes on the heap. Every malloc() must have a corresponding free() to prevent memory leaks.",
            commonMistakes = "Dereferencing a NULL pointer, reading past allocated boundaries (buffer overflow), or accessing memory after free() (use-after-free).",
            difficulty = "Intermediate",
            tags = "c, pointers, memory, malloc, free, heap, systems",
            sourceUrl = "https://en.cppreference.com/w/c/memory/malloc",
            sourceName = "C Reference Manual",
            relatedConcepts = "Stack vs Heap, Segmentation Fault, Pointer Arithmetic, Valgrind",
            isTrending = false,
            popularityScore = 89
        ),

        // 22. C# LINQ Queries
        KnowledgeEntryEntity(
            id = "csharp_linq",
            title = "C# LINQ (Language Integrated Query)",
            slug = "csharp-language-integrated-query-linq",
            definition = "A set of language extensions providing declarative, SQL-like query capabilities directly over in-memory collections and remote databases.",
            contentType = "Functions and methods",
            languageSlug = "csharp",
            languageName = "C#",
            syntax = "var results = collection.Where(x => condition).Select(x => transformation);",
            exampleCode = """using System;
using System.Linq;
using System.Collections.Generic;

public class Program {
    public static void Main() {
        var developers = new List<Developer> {
            new("Alice", "C#", 6),
            new("Bob", "Python", 3),
            new("Charlie", "C#", 8),
            new("Diana", "Go", 4)
        };

        // LINQ Method Syntax:
        var seniorCSharpDevs = developers
            .Where(d => d.Language == "C#" && d.YearsExp >= 5)
            .OrderByDescending(d => d.YearsExp)
            .Select(d => $"{d.Name} ({d.YearsExp} yrs)")
            .ToList();

        seniorCSharpDevs.ForEach(Console.WriteLine);
        // Output:
        // Charlie (8 yrs)
        // Alice (6 yrs)
    }
}

public record Developer(string Name, string Language, int YearsExp);""",
            exampleExplanation = "LINQ employs deferred execution (via IEnumerable<T> and IQueryable<T>). The query is not evaluated when defined, but when iterated (e.g. via .ToList() or foreach).",
            commonMistakes = "Evaluating multiple queries against remote EF Core databases repeatedly instead of caching with .ToList(), causing unnecessary N+1 queries.",
            difficulty = "Beginner",
            tags = "csharp, dotnet, linq, collections, functional, lambda, c#",
            sourceUrl = "https://learn.microsoft.com/en-us/dotnet/csharp/linq/",
            sourceName = "Microsoft Learn .NET Docs",
            relatedConcepts = "Deferred Execution, Lambda Expressions, Entity Framework, IQueryable",
            isTrending = false,
            popularityScore = 87
        ),

        // 23. Python Loops (Combination Code for "loop in python", "python loop", "for loop in python")
        KnowledgeEntryEntity(
            id = "py_loops_combination",
            title = "Python Loops (for, while, enumerate)",
            slug = "python-loops-for-while-enumerate",
            definition = "Fundamental looping constructs in Python: for-in for iterating over collections, while for condition-based loops, enumerate() for indexed iteration, and zip() for multi-iterable loops.",
            contentType = "Syntax and operators",
            languageSlug = "python",
            languageName = "Python",
            syntax = "for item in iterable:\n    # iterate items\n\nwhile condition:\n    # condition loop\n\nfor i, item in enumerate(items, start=0):\n    # indexed loop",
            exampleCode = """# --- 1. Basic for-in loop over a collection ---
languages = ["Python", "JavaScript", "Rust", "Kotlin", "Go"]
for lang in languages:
    print(f"CodeAtlas indexes: {lang}")

# --- 2. Loop with index using enumerate() ---
for index, lang in enumerate(languages, start=1):
    print(f"{index}. {lang}")

# --- 3. while loop with condition & decrement ---
countdown = 5
while countdown > 0:
    print(f"T-minus {countdown}...")
    countdown -= 1
print("Execution Launched!")

# --- 4. Looping over dictionary key-value pairs ---
user_skills = {"Alice": "Python", "Bob": "Kotlin", "Charlie": "Go"}
for user, skill in user_skills.items():
    print(f"{user} specializes in {skill}")

# --- 5. Looping through range with custom step ---
for step in range(0, 10, 2):
    print(f"Step: {step}")

# --- 6. Early exit (break) and skip (continue) ---
for num in range(1, 10):
    if num % 2 != 0:
        continue  # skip odd numbers
    if num == 8:
        break     # stop loop early at 8
    print(f"Processed even: {num}")""",
            exampleExplanation = "In Python, for loops iterate over any object implementing the iterator protocol (__iter__ and __next__). Use enumerate() when you need both the index and the value instead of range(len(list)). Use while loops when the termination condition is evaluated dynamically during execution.",
            commonMistakes = "Modifying (adding/deleting) a list while iterating over it, which skips items or causes unexpected behavior. Instead, loop over a shallow copy with list[:] or use list comprehensions.",
            difficulty = "Beginner",
            tags = "loop in python, python loop, for loop in python, while loop python, python looping, enumerate python, loops in python, for loop, while loop, iteration in python, python, loop, for, while",
            sourceUrl = "https://docs.python.org/3/tutorial/controlflow.html#for-statements",
            sourceName = "Python 3 Official Tutorial",
            relatedConcepts = "List Comprehensions, range(), enumerate(), zip(), break and continue, Iterators",
            isTrending = true,
            popularityScore = 100
        ),

        // 24. JavaScript Loops (Combination Code for "loop in javascript", "js loop")
        KnowledgeEntryEntity(
            id = "js_loops_combination",
            title = "JavaScript Loops (for, for...of, for...in, while)",
            slug = "javascript-loops-for-of-in-while",
            definition = "Modern looping structures in JavaScript: for...of for arrays and iterables, for...in for object keys, Array.prototype.forEach(), and classic counter for loops.",
            contentType = "Syntax and operators",
            languageSlug = "javascript",
            languageName = "JavaScript",
            syntax = "for (const item of array) { ... }\nfor (const key in object) { ... }\narray.forEach((item, index) => { ... })",
            exampleCode = """const techStack = ["TypeScript", "React", "Node.js", "GraphQL"];

// 1. for...of: Best for Arrays, Maps, Sets (values)
for (const tech of techStack) {
  console.log("Stack component: " + tech);
}

// 2. Array.prototype.forEach(): Functional iteration
techStack.forEach((tech, idx) => {
  console.log((idx + 1) + ". " + tech);
});

// 3. for...in: Iterating over Object properties
const serverConfig = { port: 8080, env: "production", ssl: true };
for (const key in serverConfig) {
  console.log(key + " -> " + serverConfig[key]);
}

// 4. while loop with condition
let attempts = 3;
while (attempts > 0) {
  console.log("Connecting... Retries left: " + attempts);
  attempts--;
}""",
            exampleExplanation = "Use for...of for arrays, strings, and generator iterables to get direct values. Use for...in exclusively for object property keys (or prefer Object.entries()). forEach cannot be broken out of with 'break'—use for...of if early exit is required.",
            commonMistakes = "Using for...in on arrays. for...in iterates over arbitrary property names and prototype keys as strings, not numeric values in guarantee order.",
            difficulty = "Beginner",
            tags = "loop in javascript, javascript loop, js loop, for of javascript, foreach javascript, while loop javascript, loop, javascript",
            sourceUrl = "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Loops_and_iteration",
            sourceName = "MDN Web Docs",
            relatedConcepts = "Array.map(), for...of vs for...in, Iterators & Generators, break and continue",
            isTrending = true,
            popularityScore = 95
        ),

        // 25. Go Loops (Combination Code for "loop in go", "golang loop")
        KnowledgeEntryEntity(
            id = "go_loops_combination",
            title = "Go Loops (for, for-range)",
            slug = "go-loops-for-range",
            definition = "Go has only ONE looping keyword: 'for'. It handles traditional 3-component loops, while-style conditional loops, infinite loops, and range iteration over slices and maps.",
            contentType = "Syntax and operators",
            languageSlug = "go",
            languageName = "Go",
            syntax = "for i := 0; i < n; i++ { ... }\nfor condition { ... }\nfor index, val := range slice { ... }",
            exampleCode = """package main

import "fmt"

func main() {
    languages := []string{"Go", "Rust", "Python", "Kotlin"}

    // 1. for-range loop (like for-each)
    for idx, lang := range languages {
        fmt.Printf("%d: %s\n", idx+1, lang)
    }

    // 2. While-style loop in Go
    counter := 3
    for counter > 0 {
        fmt.Printf("Tick %d\n", counter)
        counter--
    }

    // 3. Map iteration
    ports := map[string]int{"http": 80, "https": 443, "ssh": 22}
    for service, port := range ports {
        fmt.Printf("%s runs on port %d\n", service, port)
    }

    // 4. Traditional 3-component loop
    for i := 0; i < 5; i++ {
        if i%2 == 0 {
            continue
        }
        fmt.Println("Odd:", i)
    }
}""",
            exampleExplanation = "Go intentionally simplified language syntax by consolidating all looping paradigms into the 'for' keyword. The 'range' clause iterates over slices, arrays, maps, strings, and channels cleanly.",
            commonMistakes = "Taking the pointer of the iteration variable inside a range loop before Go 1.22, where loop variables were reused across iterations.",
            difficulty = "Beginner",
            tags = "loop in go, golang loop, for loop in go, for range go, while loop in go, go loop, loop, go",
            sourceUrl = "https://go.dev/tour/flowcontrol/1",
            sourceName = "A Tour of Go",
            relatedConcepts = "range, break, continue, Slices, Maps",
            isTrending = false,
            popularityScore = 89
        ),

        // 26. Python Functions (Combination Code for "functions in python", "def in python")
        KnowledgeEntryEntity(
            id = "py_functions_combination",
            title = "Functions in Python (def, *args, **kwargs)",
            slug = "python-functions-def-args-kwargs",
            definition = "Defining reusable blocks of logic in Python using the def keyword, supporting default parameters, arbitrary positional args (*args), keyword args (**kwargs), and return values.",
            contentType = "Functions and methods",
            languageSlug = "python",
            languageName = "Python",
            syntax = "def function_name(param1, param2=default, *args, **kwargs):\n    return result",
            exampleCode = """# 1. Standard function with type hints
def calculate_discount(price: float, discount_percent: float = 10.0) -> float:
    return price * (1 - discount_percent / 100)

print(calculate_discount(100.0))       # 90.0 (default 10%)
print(calculate_discount(100.0, 25.0)) # 75.0

# 2. Arbitrary arguments (*args) and keyword arguments (**kwargs)
def log_request(endpoint: str, *tags, **metadata):
    print(f"Request: {endpoint}")
    print(f"Tags: {', '.join(tags)}")
    for key, val in metadata.items():
        print(f"  {key}: {val}")

log_request(
    "/api/v1/search",
    "search", "atlas", "fast",
    user_id="usr_123", status=200, latency_ms=42
)

# 3. Returning multiple values as a tuple
def get_min_max(numbers: list[int]) -> tuple[int, int]:
    return min(numbers), max(numbers)

low, high = get_min_max([12, 5, 89, 44, 2])
print(f"Range: {low} to {high}")""",
            exampleExplanation = "Functions in Python are first-class citizens. You can assign them to variables, pass them as arguments to higher-order functions, and return them. *args captures extra positional arguments as a tuple, while **kwargs captures extra keyword arguments as a dict.",
            commonMistakes = "Using mutable default arguments like def append_to(item, target=[]). The default list is instantiated once at function definition time, not per call. Use target=None instead.",
            difficulty = "Beginner",
            tags = "functions in python, python functions, def in python, define function python, python def, python methods, args kwargs python, python, function",
            sourceUrl = "https://docs.python.org/3/tutorial/controlflow.html#defining-functions",
            sourceName = "Python 3 Official Docs",
            relatedConcepts = "Lambda Functions, Decorators, Scope & LEGB, Generators",
            isTrending = true,
            popularityScore = 96
        ),

        // 27. Python Error Handling (Combination Code for "error handling in python", "try catch in python")
        KnowledgeEntryEntity(
            id = "py_try_except_combination",
            title = "Error Handling in Python (try, except, finally)",
            slug = "python-error-handling-try-except-finally",
            definition = "Graceful exception handling in Python using try, except, else, and finally clauses to prevent crashes from unhandled errors.",
            contentType = "Errors and debugging",
            languageSlug = "python",
            languageName = "Python",
            syntax = "try:\n    # risky operation\nexcept SpecificError as e:\n    # handle error\nelse:\n    # runs if no error\nfinally:\n    # always runs cleanup",
            exampleCode = """import json

def parse_user_payload(raw_json: str):
    try:
        # Risky parsing operation
        data = json.loads(raw_json)
        username = data["user"]["name"]
        age = int(data["user"]["age"])
    except json.JSONDecodeError as e:
        print(f"Invalid JSON format: {e.msg}")
        return None
    except KeyError as e:
        print(f"Missing required field: {e}")
        return None
    except ValueError as e:
        print(f"Invalid age data type: {e}")
        return None
    else:
        # Executes ONLY when no exceptions occurred
        print(f"Successfully parsed user: {username}, age {age}")
        return data
    finally:
        # ALWAYS runs (e.g. closing sockets or freeing resources)
        print("Parsing attempt completed.")

# Test valid & invalid payloads
parse_user_payload('{"user": {"name": "Zane", "age": "24"}}')
parse_user_payload('{"invalid": true}')""",
            exampleExplanation = "Catch specific exceptions rather than bare 'except:', which would inadvertently intercept KeyboardInterrupt (Ctrl+C) and SystemExit. The 'else' block isolates code that should run only upon success, keeping the 'try' block as lean as possible.",
            commonMistakes = "Using a bare 'except:' clause or catching Exception without logging or re-raising, effectively silencing critical bugs and memory errors.",
            difficulty = "Beginner",
            tags = "error handling in python, try except in python, python try catch, exceptions in python, try except python, python error, error, python",
            sourceUrl = "https://docs.python.org/3/tutorial/errors.html#handling-exceptions",
            sourceName = "Python 3 Exception Handling Guide",
            relatedConcepts = "Custom Exceptions, Tracebacks, Context Managers, raise statement",
            isTrending = true,
            popularityScore = 94
        )
    )
}
