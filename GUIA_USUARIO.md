# 🎮 Guía del Usuario - Ajedrez Java

## 🚀 Inicio Rápido

### Primer Uso
1. **Ejecutar el juego**: Haz doble clic en `Main.java` o ejecuta desde tu IDE
2. **Seleccionar modo**: Elige entre "Jugador vs Jugador" o "Jugador vs Bot"
3. **¡A jugar!**: El tablero se abrirá listo para comenzar

### Controles Básicos
- **👆 Clic izquierdo**: Seleccionar pieza / Mover a casilla
- **🔄 Clic en pieza seleccionada**: Cancelar selección
- **✨ Borde azul**: Indica la pieza actualmente seleccionada

## 🎯 Modos de Juego

### 👥 Jugador vs Jugador (1v1)
- **Turnos alternados**: Las blancas siempre comienzan
- **Control total**: Ambos jugadores controlan sus piezas
- **Ideal para**: Partidas locales entre amigos

### 🤖 Jugador vs Bot (PvE)
- **Tú juegas**: Siempre con las piezas blancas
- **Bot juega**: Automáticamente con las piezas negras
- **Nivel**: Principiante (perfecto para aprender)
- **Respuesta**: El bot piensa ~0.5 segundos antes de mover

## ♟️ Cómo Mover las Piezas

### Movimiento Básico
1. **Haz clic** en la pieza que quieres mover
   - La pieza se resalta con un borde azul
2. **Haz clic** en la casilla de destino
   - Si el movimiento es válido, la pieza se mueve
   - Si es inválido, aparece un mensaje de error

### Capturas
- **Automático**: Si hay una pieza enemiga en la casilla destino, se captura automáticamente
- **Visual**: La pieza capturada desaparece del tablero

### Cancelar Movimiento
- **Clic en la misma pieza**: Cancela la selección actual
- **Clic en otra pieza tuya**: Cambia la selección

## 🏰 Reglas Especiales

### Enroque
El enroque es un movimiento especial que involucra al rey y una torre.

#### ¿Cómo hacer enroque?
1. **Selecciona tu rey** (debe estar en su posición inicial)
2. **Mueve el rey 2 casillas** hacia la torre con la que quieres enrocar
   - **Derecha**: Enroque corto (con torre del lado del rey)
   - **Izquierda**: Enroque largo (con torre del lado de la reina)

#### Condiciones para el enroque:
- ✅ El rey no se ha movido nunca
- ✅ La torre no se ha movido nunca  
- ✅ No hay piezas entre el rey y la torre
- ✅ El rey no está en jaque
- ✅ El rey no pasa por casillas atacadas

#### Ejemplo Visual:
```
Antes del enroque corto:
...R...K  (R=Torre, K=Rey)

Después del enroque corto:
.....RK.  (Rey y torre intercambian posiciones)
```

### 👑 Coronación de Peones

#### ¿Cuándo ocurre?
- Cuando tu peón llega al final del tablero (fila 1 para blancas, fila 8 para negras)

#### ¿Cómo elegir la pieza?
1. **Mueve tu peón** a la última fila
2. **Aparece un menú** con 4 opciones:
   - 👸 **Reina** (recomendado - más poderosa)
   - 🏰 **Torre** (movimiento horizontal/vertical)
   - 🔷 **Alfil** (movimiento diagonal)  
   - 🐎 **Caballo** (movimiento en L)
3. **Selecciona tu elección** y haz clic en "OK"
4. **Tu peón se transforma** en la pieza elegida

#### Estrategia:
- **Reina**: La mejor opción en el 95% de los casos
- **Caballo**: Útil para jaque mate específicos
- **Torre/Alfil**: Ocasionalmente útiles en finales especiales

## ⚡ Situaciones de Jaque

### ¿Qué es jaque?
- Tu rey está siendo atacado por una pieza enemiga
- **Aparece un mensaje**: "¡Jaque al jugador [color]!"
- **Debes salir del jaque** en tu próximo movimiento

### ¿Cómo salir del jaque?
Tienes 3 opciones:

1. **Mover el rey** a una casilla segura
2. **Bloquear** el ataque con otra pieza
3. **Capturar** la pieza que está dando jaque

### Jaque Mate
- **¿Qué es?**: No puedes salir del jaque de ninguna manera
- **Resultado**: ¡Has perdido la partida!
- **El juego termina** automáticamente

## 🤖 Jugando Contra el Bot

### Características del Bot
- **Nivel**: Principiante amigable
- **Comportamiento**: Comete errores realistas
- **Velocidad**: Responde en ~0.5 segundos
- **Estilo**: Movimientos variados y algo impredecibles

### ¿Qué hace bien el Bot?
- ✅ Escapa cuando su rey está en jaque
- ✅ Busca capturas obvias
- ✅ Mueve peones hacia adelante
- ✅ Desarrolla piezas gradualmente

### ¿Qué errores comete?
- ❌ A veces ignora amenazas
- ❌ No siempre hace los mejores movimientos
- ❌ Puede ser "distraído" (20% del tiempo)
- ❌ No planifica a largo plazo

### Consejos para ganarle al Bot:
1. **Desarrolla tus piezas** rápidamente
2. **Protege tu rey** con enroque temprano
3. **Busca capturas** que el bot pueda pasar por alto
4. **Controla el centro** del tablero
5. **Ten paciencia** - el bot cometerá errores

## 🎨 Interfaz Visual

### Colores del Tablero
- **Casillas claras**: Beige claro (#F0D9B5)
- **Casillas oscuras**: Marrón (#B58863)
- **Pieza seleccionada**: Borde azul grueso

### Piezas
- **Gráficos claros**: Cada pieza tiene su imagen distintiva
- **Tamaño consistente**: 60x60 píxeles, perfectamente escaladas
- **Fácil identificación**: Colores y formas tradicionales

## 🔧 Solución de Problemas

### "No puedo mover mi pieza"
**Posibles causas:**
- ❌ No es tu turno
- ❌ Movimiento inválido para esa pieza
- ❌ Tu rey quedaría en jaque
- ❌ Hay una pieza bloqueando el camino

**Solución:** Verifica las reglas de la pieza y asegúrate de que sea tu turno

### "El enroque no funciona"
**Posibles causas:**
- ❌ El rey o la torre ya se movieron
- ❌ Hay piezas en el camino
- ❌ El rey está en jaque
- ❌ El rey pasaría por una casilla atacada

**Solución:** Verifica todas las condiciones del enroque

### "Las imágenes no aparecen"
**Causa:** Carpeta de recursos mal ubicada
**Solución:** Asegúrate de que la carpeta `resources/` esté en la ubicación correcta

### "El juego se cierra inesperadamente"
**Causa:** Error en el código
**Solución:** Ejecuta desde un IDE para ver los mensajes de error

## 📚 Reglas Básicas del Ajedrez

### Objetivo
- **Ganar**: Dar jaque mate al rey enemigo
- **Empate**: Situaciones de tablas (no implementado aún)

### Movimiento de Piezas

#### ♟️ Peón
- **Adelante**: 1 casilla (2 en primer movimiento)
- **Captura**: 1 casilla en diagonal
- **Especial**: Coronación al llegar al final

#### 🏰 Torre
- **Movimiento**: Horizontal y vertical, cualquier distancia
- **Captura**: Igual que movimiento
- **Especial**: Participa en el enroque

#### 🐎 Caballo
- **Movimiento**: En "L" (2+1 o 1+2 casillas)
- **Único**: Puede saltar sobre otras piezas
- **Captura**: En la casilla de destino

#### 🔷 Alfil
- **Movimiento**: Diagonal, cualquier distancia
- **Limitación**: Solo casillas del mismo color
- **Captura**: Igual que movimiento

#### 👸 Reina
- **Movimiento**: Combinación de torre + alfil
- **Más poderosa**: La pieza más versátil
- **Captura**: Igual que movimiento

#### 👑 Rey
- **Movimiento**: 1 casilla en cualquier dirección
- **Especial**: Enroque (con condiciones)
- **Importante**: No puede moverse a jaque

## 💡 Consejos y Estrategias

### Para Principiantes
1. **Aprende el valor** de cada pieza:
   - Peón = 1 punto
   - Caballo/Alfil = 3 puntos
   - Torre = 5 puntos
   - Reina = 9 puntos
   - Rey = ¡invaluable!

2. **Principios de apertura**:
   - Desarrolla piezas menores primero
   - Controla el centro
   - Enroca temprano para proteger al rey

3. **Táctica básica**:
   - Busca capturas "gratis"
   - Protege tus piezas
   - Ataca piezas no defendidas

### Contra el Bot
1. **Sé paciente**: El bot cometerá errores
2. **Desarrolla rápido**: Saca tus piezas del fondo
3. **Busca táctica**: Ataques dobles, clavadas, etc.
4. **Final de juego**: Aprende mates básicos

## 🎉 ¡Diviértete!

El ajedrez es un juego de por vida. No te preocupes por perder las primeras partidas - ¡hasta los grandes maestros pierden! Lo importante es:

- 🧠 **Aprender** de cada partida
- 🎯 **Practicar** regularmente  
- 📚 **Estudiar** tácticas básicas
- 😊 **Disfrutar** el proceso

¡Que tengas excelentes partidas! ♟️👑
