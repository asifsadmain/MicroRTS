package ai.synthesis.LLM;

/**
 *
 * @author quazi
 */


import ai.synthesis.ComplexDSL.LS_Actions.*;
import ai.synthesis.ComplexDSL.LS_CFG.*;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG.*;
import ai.synthesis.ComplexDSL.Synthesis_Base.CFG_Condition.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ASTCreatorForComplexDSL {

    public static S_LS AST = new S_LS();
    public static Object currentNode = AST;

    public static List<String> extractContentInBrackets(String str) {
        // Find the starting and ending indices of the brackets
        int start = str.indexOf('(');
        int end = str.lastIndexOf(')');

        // If there are no brackets or they are not properly paired, return an empty list
        if (start == -1 || end == -1 || end < start) {
            return Collections.emptyList();
        }

        // Extract the string inside the brackets
        String insideBrackets = str.substring(start + 1, end);

        // Split the string by comma and add each part to the list
        String[] parts = insideBrackets.split(",\\s*");
        return Arrays.asList(parts);
    }

    public static String getBooleanCondition (String expr) {
        String regex = "\\(.*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expr);

        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

    public static B_LS createBooleanNode(String condition) {
        condition = condition.substring(1, condition.length()-1);
        List<String> parameters = extractContentInBrackets(condition);        // True if encrypted DSL
        B_LS b = new B_LS();

        if (condition.startsWith("u.hasNumberOfUnits") || condition.startsWith("b1")) {
            b = new B_LS(new HasNumberOfUnits(new Type(parameters.get(0)), new N(parameters.get(1))));
        } else if (condition.startsWith("u.opponentHasNumberOfUnits") || condition.startsWith("b2")) {
            b = new B_LS(new OpponentHasNumberOfUnits(new Type(parameters.get(0)), new N(parameters.get(1))));
        } else if (condition.startsWith("u.hasLessNumberOfUnits") || condition.startsWith("b3")) {
            b = new B_LS(new HasLessNumberOfUnits(new Type(parameters.get(0)), new N(parameters.get(1))));
        } else if (condition.startsWith("u.haveQtdUnitsAttacking") || condition.startsWith("b4")) {
            b = new B_LS(new HaveQtdUnitsAttacking(new N(parameters.get(0))));
        } else if (condition.startsWith("u.hasUnitWithinDistanceFromOpponent") || condition.startsWith("b5")) {
            b = new B_LS(new HasUnitWithinDistanceFromOpponent(new N(parameters.get(0))));
        } else if (condition.startsWith("u.hasNumberOfWorkersHarvesting") || condition.startsWith("b6")) {
            b = new B_LS(new HasNumberOfWorkersHarvesting(new N(parameters.get(0))));
        } else if (condition.startsWith("u.isBuilder") || condition.startsWith("b8")) {
            b = new B_LS(new Is_Builder());
        } else if (condition.startsWith("u.is") || condition.startsWith("b7")) {
            b = new B_LS(new is_Type(new Type(parameters.get(0))));
        } else if (condition.startsWith("u.canAttack") || condition.startsWith("b9")) {
            b = new B_LS(new CanAttack());
        } else if (condition.startsWith("u.hasUnitThatKillsInOneAttack") || condition.startsWith("b10")) {
            b = new B_LS(new HasUnitThatKillsInOneAttack());
        } else if (condition.startsWith("u.opponentHasUnitThatKillsUnitInOneAttack") || condition.startsWith("b11")) {
            b = new B_LS(new OpponentHasUnitThatKillsUnitInOneAttack());
        } else if (condition.startsWith("u.hasUnitInOpponentRange") || condition.startsWith("b12")) {
            b = new B_LS(new HasUnitInOpponentRange());
        } else if (condition.startsWith("u.opponentHasUnitInPlayerRange") || condition.startsWith("b13")) {
            b = new B_LS(new OpponentHasUnitInPlayerRange());
        } else if (condition.startsWith("u.canHarvest") || condition.startsWith("b14")) {
            b = new B_LS(new CanHarvest());
        } else {
            return null;
        }

        return b;
    }

    public static S_LS createCommandNode(String command) {
        List<String> parameters = extractContentInBrackets(command);  // true if encrypted DSL
        S_LS s = new S_LS();

        if (command.startsWith("u.build") || command.startsWith("u.c1")) {
            s = new S_LS(new C_LS(new Build_LS(new Type(parameters.get(0)), new Direction(parameters.get(1)), new N(parameters.get(2)))));
        } else if (command.startsWith("u.train") || command.startsWith("u.c2")) {
            s = new S_LS(new C_LS(new Train_LS(new Type(parameters.get(0)), new Direction(parameters.get(1)), new N(parameters.get(2)))));
        } else if (command.startsWith("u.moveToUnit") || command.startsWith("u.c3")) {
            s = new S_LS(new C_LS(new moveToUnit_LS(new TargetPlayer(parameters.get(0)), new OpponentPolicy(parameters.get(1)))));
        } else if (command.startsWith("u.attackIfInRange")  || command.startsWith("u.idle") || command.startsWith("u.c6")) {
            s = new S_LS(new C_LS(new Idle_LS()));
        } else if (command.startsWith("u.attack") || command.startsWith("u.c4")) {
            s = new S_LS(new C_LS(new Attack_LS(new OpponentPolicy(parameters.get(0)))));
        } else if (command.startsWith("u.harvest") || command.startsWith("u.c5")) {
            s = new S_LS(new C_LS(new Harvest_LS(new N(parameters.get(0)))));
        } else if (command.startsWith("u.moveAway") || command.startsWith("u.c7")) {
            s = new S_LS(new C_LS(new MoveAway_LS()));
        } else if (command.startsWith("empty")) {
            s = new S_LS(new Empty_LS());
        } else {
            return null;
        }

        return s;
    }

    public static List<Object> getParenthesisElements(String expression) {
        // Create a stack to store the curly braces and elements inside them
        Stack<List<Object>> stack = new Stack<>();
        // Current list to capture the elements inside the current pair of curly braces
        List<Object> currentList = new ArrayList<>();

        // Loop through each character in the expression
        for (char ch : expression.toCharArray()) {
            if (ch == '{') {
                // Push the current list onto the stack and start a new list
                stack.push(currentList);
                currentList = new ArrayList<>();
            } else if (ch == '}') {
                // When encountering a closing curly brace, finalize the current list
                // If stack is empty, it means there is no matching opening curly brace (unbalanced)
                List<Object> tmpCurrentList = currentList;
                currentList = stack.isEmpty() ? new ArrayList<>() : stack.pop();
                currentList.add(tmpCurrentList); // Add the nested list to the parent list
            } else if (ch == '(' || ch == '[') {
                // Only check balance, no new list creation
                if (!stack.isEmpty()) {
                    currentList.add(String.valueOf(ch));
                }
            } else if (ch == ')' || ch == ']') {
                // Only check balance, no new list creation
                if (!stack.isEmpty()) {
                    currentList.add(String.valueOf(ch));
                }
            } else if (ch == '\n') {
                // Capture newline characters to the current list
                currentList.add("\\n");
            } else if (!Character.isWhitespace(ch) || ch == ' ') {
                // Capture non-whitespace characters to the current list
                currentList.add(Character.toString(ch));
            }
        }

        // If the stack isn't empty, it means there are unmatched opening curly braces
        if (!stack.isEmpty()) {
            System.out.println("Unbalanced parentheses");
            return Collections.emptyList();
        }

        return currentList;
    }

    // Function to combine characters into strings inside the nested list
    public static List<Object> combineCharacters(List<Object> list) {
        List<Object> result = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();

        for (Object obj : list) {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.equals("\\n")) {
                    if (currentString.length() > 0) {
                        result.add(currentString.toString());
                        currentString = new StringBuilder();
                    }
                    result.add(str);
                } else {
                    currentString.append(str);
                }
            } else if (obj instanceof List<?>) {
                if (currentString.length() > 0) {
                    result.add(currentString.toString());
                    currentString = new StringBuilder();
                }
                result.add(combineCharacters((List<Object>) obj));
            }
        }

        if (currentString.length() > 0) {
            result.add(currentString.toString());
        }

        return result;
    }

    // Function to remove all '\n' occurrences from the combined list
    public static List<Object> removeNewLines(List<Object> list) {
        List<Object> result = new ArrayList<>();

        for (Object obj : list) {
            if (obj instanceof String) {
                if (!obj.equals("\\n")) {
                    result.add(obj);
                }
            } else if (obj instanceof List<?>) {
                result.add(removeNewLines((List<Object>) obj));
            }
        }

        return result;
    }

    // Function to strip extra spaces and remove empty elements from each element in the nested list
    public static List<Object> stripExtraSpacesAndRemoveEmpty(List<Object> list) {
        List<Object> result = new ArrayList<>();

        for (Object obj : list) {
            if (obj instanceof String) {
                String trimmedStr = ((String) obj).trim();
                if (!trimmedStr.isEmpty()) {
                    result.add(trimmedStr);
                }
            } else if (obj instanceof List<?>) {
                List<Object> nestedResult = stripExtraSpacesAndRemoveEmpty((List<Object>) obj);
                if (!nestedResult.isEmpty()) {
                    result.add(nestedResult);
                }
            }
        }

        return result;
    }

    // Function to print lists recursively: innermost list first, outermost last
    public static void createAST(List<Object> list, int level) {
        boolean hasList = false;
        boolean hasString = false;

        System.out.println(list);

        for (Object obj : list) {
            if (obj instanceof List<?>) {
                hasList = true;
            } else if (obj instanceof String) {
                hasString = true;
            }
        }
        if (hasString && hasList) {
//            System.out.println("Has both");
            List<String> keywords = new ArrayList<String>();

            for (Object obj : list) {
                if (obj instanceof String) {
                    keywords.add((String) obj);
                }
            }

            for (String keyword : keywords) {
                if ((keyword != null) && keyword.contains("for")) {
//                    System.out.println("Has For Loop");
                    For_S_LS forS = new For_S_LS(null);
                    if (currentNode instanceof S_LS) {
                        ((S_LS) currentNode).setChild(forS);
                        currentNode = ((S_LS) currentNode).getChild();
                    }
                } else if ((keyword != null) && keyword.contains("if")) {
                    String condition = getBooleanCondition(keyword);
//                    System.out.println("Condition: " + condition);
                    S_LS parentS = new S_LS();
                    if (list.size() > 2) {
                        if ((list.get(2) instanceof String) && ((String) list.get(2)).contains("else")) {
//                            System.out.println("Has If-Else block");
                            If_B_then_S_else_S_LS ifThenElse = new If_B_then_S_else_S_LS();
                            ifThenElse.setB(createBooleanNode(condition));
                            parentS.setChild(ifThenElse);
                        }
                    } else {
//                        System.out.println("Has If block");
                        If_B_then_S_LS ifThen = new If_B_then_S_LS();
                        ifThen.setB(createBooleanNode(condition));
                        parentS.setChild(ifThen);
                    }

                    if (currentNode instanceof For_S_LS) {
                        ((For_S_LS) currentNode).setChild(parentS);
                        currentNode = ((For_S_LS) currentNode).getChild();
                    }
                    if (currentNode instanceof S_LS) {
                        currentNode = ((S_LS) currentNode).getChild();
                    }
                    if (currentNode instanceof If_B_then_S_LS) {
                        ((If_B_then_S_LS) currentNode).setS(parentS);
                        currentNode = ((If_B_then_S_LS) currentNode).getS();
                    }

//                    System.out.println(currentNode.getClass());
                }
            }
        } else if (hasString && !hasList) {
//            System.out.println("Has only string");
            S_LS parentS = new S_LS();
            List <S_LS> commandList = new ArrayList<S_LS>();

            for (Object obj : list) {
                List<String> parameters = extractContentInBrackets((String) obj);
//                System.out.println("Parameters: " + parameters);
                S_LS commandS = createCommandNode((String) obj);
                commandList.add(commandS);
            }

            parentS = commandList.get(0);
            if (commandList.size() > 1) {
                for (int i = 1; i < commandList.size(); i++) {
                    parentS = new S_LS(new S_S_LS(parentS, commandList.get(i)));
                }
            }
            if (currentNode instanceof For_S_LS) {
                ((For_S_LS) currentNode).setChild(parentS);
                currentNode = ((For_S_LS) currentNode).getChild();
//                System.out.println(((S_LS) currentNode).translateIndentation(1));
            }
            if (currentNode instanceof If_B_then_S_LS) {
                ((If_B_then_S_LS) currentNode).setS(parentS);
                currentNode = ((If_B_then_S_LS) currentNode).getS();
            }
            if (currentNode instanceof S_LS) {
                ((S_LS) currentNode).setChild(parentS);
                currentNode = ((S_LS) currentNode).getChild();
//                System.out.println(((S_LS) currentNode).translateIndentation(1));
            }

//            System.out.println(parentS.translateIndentation(1));
        } else if (!hasString && hasList){
//            System.out.println("Has only List");
        }

        for (Object obj : list) {
            if (obj instanceof List<?>) {
                createAST((List<Object>) obj, level + 1);
            }
        }

        // Print the current list after all nested lists are printed
//        System.out.print("{");
//        S_LS S = null;
//        for (Object obj : list) {
//            if (obj instanceof List<?>) {
//                System.out.print("Level" + level + "S");
//            } else {
//                System.out.print(level + ":" +obj);
//
//                if (obj.equals("u.harvest(2)")) {
//                    S = new S_LS(new C_LS(new Harvest_LS(new N("2"))));
//                } else if (obj.equals("u.attack(Closest)")) {
//                    S = new S_LS(new C_LS(new Attack_LS(new OpponentPolicy("Closest"))));
//                }
//                if (S != null) {
//                    ListOfS.add(S);
//                }
//            }
//        }
//        System.out.print("}");
    }

    public static void main(String[] args) {
        // Test case
        String testCase1 = "for(Unit u) {\n" +
                "  if(u.canHarvest()) {\n" +
                "    u.harvest(2)\n" +
                "    u.attack(Weakest)\n" +
                "  }\n" +
                "else {\n" +
                "    u.attack(Closest)\n" +
                "  }\n" +
                "}";

        String testCase2 = "for(Unit u) {\n" +
                "        if(u.hasNumberOfUnits(Worker, 0)) {\n" +
                "            u.train(Worker, Down, 1)\n" +
                "        } else {\n" +
                "            if(u.hasLessNumberOfUnits(Worker, 5)) {\n" +
                "                u.train(Worker, Down, 1)\n" +
                "            }\n" +
                "        }\n" +
                "}";

        String testCase3 = "for(Unit u) {\n" +
                "if(u.hasNumberOfUnits(Worker, 0)) {\n" +
                "            u.train(Worker, Down, 1)\n" +
                "            u.harvest(3)\n" +
                "            if(u.canHarvest()) {\n" +
                "               u.harvest(2)\n" +
                "               u.attack(Weakest)\n" +
                "            }\n" +
                "        }\n" +
                "}";

        String testCase4 = "for(Unit u) {\n" +
                "if(u.hasNumberOfUnits(Worker, 0)) {\n" +
                "            if(u.canHarvest()) {\n" +
                "            u.train(Worker, Down, 1)\n" +
                "            u.harvest(3)\n" +
                "               u.harvest(2)\n" +
                "               u.attack(Weakest)\n" +
                "            }\n" +
                "        }\n" +
                "}";

        String testCase5 = "for(Unit u) {\n" +
                "    if(u.hasNumberOfWorkersHarvesting(5)) {\n" +
                "        if(u.hasLessNumberOfUnits(Worker, 10)) {\n" +
                "            if(u.canHarvest()) {\n" +
                "                u.build(Worker, Right, 1);\n" +
                "                u.attack(Weakest)\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        List<Object> result = getParenthesisElements(testCase5);

        // Print the nested list structure capturing all the elements inside each curly brace including newlines
//        System.out.println("Elements inside parentheses: " + result);
//        printNestedList(result, 0);
//        System.out.println();

        // Combine characters within each list based on constraints
        List<Object> combinedResult = combineCharacters(result);
//        System.out.println("Combined elements inside parentheses: " + combinedResult);
//        printNestedList(combinedResult, 0);
//        System.out.println();

        // Remove all occurrences of '\n' from the combined list
        List<Object> finalResult = removeNewLines(combinedResult);
//        System.out.println("Final elements inside parentheses without '\\n': " + finalResult);
//        printNestedList(finalResult, 0);
//        System.out.println();

        // Strip extra spaces from each element in the list 'finalResult' and remove empty elements
        List<Object> strippedResult = stripExtraSpacesAndRemoveEmpty(finalResult);
//        System.out.println("Final elements inside parentheses without '\\n' and extra spaces stripped: " + strippedResult);
//        printNestedList(strippedResult, 0);
//        System.out.println();

        // Print lists recursively
//        System.out.println("Recursive print of nested lists (innermost first):");
        createAST(strippedResult, 0);
//        System.out.println();
//        for (S_LS S : ListOfS) {
//            System.out.println(S.translateIndentation(1));
//        }
        System.out.println("Compiled Program:");
        System.out.println(AST.translateIndentation(1));
    }

    // Helper function to print nested lists
    public static void printNestedList(List<Object> list, int level) {
        for (Object obj : list) {
            if (obj instanceof List<?>) {
                System.out.print("{");
                printNestedList((List<Object>) obj, level + 1);
                System.out.print("}");
            } else {
                System.out.print(obj);
            }
        }
    }
}
