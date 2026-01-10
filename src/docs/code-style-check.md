
RUN:<br>
(without db)<br>
.\mvnw.cmd verify -DskipTests <br>
(with db)<br>
.\mvnw.cmd verify <br>
test if code style is consistent

current Checkstyle config enforces the following rules:

At the top-level (Checker):

* NewlineAtEndOfFile: Ensures every file ends with a newline.
* FileTabCharacter: Disallows tab characters in files (so you must use spaces).

Inside TreeWalker (which analyzes Java syntax tree):

* TypeName: Checks class/interface/enum names follow naming conventions (usually PascalCase).
* MethodName: Checks method names follow naming conventions (usually camelCase).
* ParameterName: Checks method parameter names follow conventions (usually camelCase).
* LocalVariableName: Checks local variable names follow conventions.
* MemberName: Checks member (field) names follow conventions.
* WhitespaceAfter: Checks for required whitespace after keywords, commas, etc.
* WhitespaceAround: Checks for whitespace around operators, parentheses, etc.
* ModifierOrder: Enforces a specific order of modifiers (e.g., public static final).
* AvoidStarImport: Disallows wildcard imports like import java.util.*;.
* ConstantName: Checks that constants (static final fields) follow naming conventions (usually UPPER_CASE).