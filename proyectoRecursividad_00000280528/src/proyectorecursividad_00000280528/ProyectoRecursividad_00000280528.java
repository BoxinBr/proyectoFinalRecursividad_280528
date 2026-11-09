package proyectorecursividad_00000280528;

public class ProyectoRecursividad_00000280528 {

    public static void main(String[] args) {
        System.out.println("10+28-10 = " +calcularEcuacion("10+28-10"));
        System.out.println("9-5*(7-6)+10 = " + calcularEcuacion("9-5*(7-6)+10"));
        System.out.println("6+7+(3+4*(4-1)+9)-3 = " + calcularEcuacion("6+7+(3+4*(4-1)+9)-3"));
        System.out.println("7-4*(129-45+(12+35+(0+2)*8)+4)+2 = " + calcularEcuacion("7-4*(129-45+(12+35+(0+2)*8)+4)+2"));
        System.out.println(calcularEcuacion("5++1+10")); // en esta sección el código lanzará una excepción y terminará el programa
    }
    
    
    public static int calcularEcuacion(String ecuacion) {
        ecuacion = ecuacion.replaceAll("\\s+", "");
        
        // Validaciones del formato del String.
        if (ecuacion.isEmpty() || !ecuacion.matches("^[0-9+\\-*()]+$")) {
            throw new IllegalArgumentException("Caracteres no permitidos");
        }

        if (ecuacion.matches(".*[+\\-*]{2,}.*") ||
            ecuacion.matches("^[+\\-*].*") ||
            ecuacion.matches(".*[+\\-*]$") ||
            ecuacion.matches(".*\\([+\\-*].*") ||
            ecuacion.matches(".*[+\\-*]\\).*") ||
            ecuacion.contains("()")) {
            throw new IllegalArgumentException("Error de sintaxis");
        }
        
        
        // Verifica el nivel de paréntesis en el que se encuentran los números.
        int nivel = 0;
        
        for (int i = 0; i < ecuacion.length(); i++) {
            if (ecuacion.charAt(i) == '(') nivel++;
            if (ecuacion.charAt(i) == ')') nivel--;
            if (nivel < 0) throw new IllegalArgumentException("Error en los parentesis");
            
        }
        
        if (nivel != 0) throw new IllegalArgumentException("Error en los parentesis");
        
        // Verifica si hay números envueltos entre paréntesis.
        
        boolean estaEnvuelto = ecuacion.startsWith("(") && ecuacion.endsWith(")");
        
        if (estaEnvuelto && ecuacion.length() >= 2) {
            int balance = 0;
            boolean envueltoValido = true;
            
            for (int i = 0; i < ecuacion.length() - 1; i++) {
                if (ecuacion.charAt(i) == '(') balance++;
                if (ecuacion.charAt(i) == ')') balance--;
                
                if (balance == 0) {
                    envueltoValido = false;
                    break;
                }
            }    
            
            if (envueltoValido) {
                return calcularEcuacion(ecuacion.substring(1, ecuacion.length() - 1));
            }
        }
        
        int indiceSumayResta = -1;
        int indiceMulti = -1;
        int parentesis = 0;
        
        for (int i = ecuacion.length() - 1; i >= 0; i--) {
            char c = ecuacion.charAt(i);
            
            if (c == '(') {
                parentesis++;
            } else if (c == ')') {
                parentesis--;
            } else if (parentesis == 0) {
                if ((c == '+' || c == '-') && indiceSumayResta == -1) {
                    indiceSumayResta = i;
                } else if (c == '*' && indiceMulti == -1) {
                    indiceMulti = i;
                }
            }
        }
        
        // Resuelve las ecuaciones.
        
        if (indiceSumayResta > 0 && indiceSumayResta < ecuacion.length() - 1) {
            char operador = ecuacion.charAt(indiceSumayResta);
            String izquierda = ecuacion.substring(0, indiceSumayResta);
            String derecha = ecuacion.substring(indiceSumayResta + 1);
            
            if (operador == '+') {
                return calcularEcuacion(izquierda) + calcularEcuacion(derecha);
            } else {
                return calcularEcuacion(izquierda) - calcularEcuacion(derecha);
            }
        }

        if (indiceMulti > 0 && indiceMulti < ecuacion.length() - 1) {
            String izquierda = ecuacion.substring(0, indiceMulti);
            String derecha = ecuacion.substring(indiceMulti + 1);
            return calcularEcuacion(izquierda) * calcularEcuacion(derecha);
        }

        return Integer.parseInt(ecuacion);
    }
}
