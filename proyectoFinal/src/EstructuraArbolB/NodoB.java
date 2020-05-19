package EstructuraArbolB;

public class NodoB {

    Nodo[] datos;
    NodoB padre;
    int size;
    final int NoNodo;

    public NodoB(int NoNodo) {
        this.padre = null;
        this.datos = new Nodo[5];
        this.size = 0;
        this.NoNodo = NoNodo;

    }

    public int getSize() {
        return size;
    }

    //Metodo recursivo para buscar posicion a insertar el nodo
    public void buscarNodo(NodoB aux, ruta rta) {
        double clave1 = rta.getClave();
        if (aux.size == 0) {
            //Si esta vacio
            aux.agregarDato(rta);
            return;
        }
        for (int i = 0; i < aux.size; i++) {
            //Leer arrglo de nodos
            Nodo tmp = aux.datos[i];
            double clave2 = tmp.getDato().getClave();
            //Si la clave ya existe
            if (clave1 == clave2) {
                return;
            }
            //Comparacion
            if (clave1 < clave2) {
                if (aux.datos[i].hijoIzq != null) {
                    //Mientras no este vacio seguir buscando
                    aux.datos[i].hijoIzq.buscarNodo(aux.datos[i].hijoIzq, rta);
                    break;
                } else {
                    //Agregar
                    aux.agregarDato(rta);
                    break;

                }
            }
            //Si esta en el ultimo arreglo
            if (aux.size == i + 1) {
                if (aux.datos[i].hijoDer != null) {
                    //buscar en la derecha
                    aux.datos[i].hijoDer.buscarNodo(aux.datos[i].hijoDer, rta);
                    break;
                } else {
                    aux.agregarDato(rta);
                    break;
                }
            }

        }
    }

    private void agregarDato(ruta rta) {
        if (size < 4) {
            add(rta);
        } else {
            add(rta);
            dividir(this);
        }
    }

    private void add(ruta rta) {
        this.size++;
        this.datos[size - 1] = new Nodo(rta, size);
        //Orfenar datos
        ordenarDatos(true, this);
    }

    private void ordenarDatos(boolean v, NodoB href) {
        for (int i = 0; i < size; i++) {
            Nodo tmp = href.datos[i];
            for (int j = i + 1; j < size; j++) {
                try {
                    Nodo t2 = href.datos[j];
                    if (tmp.getDato().getClave() > t2.getDato().getClave()) {
                        if (v) {
                            //Mover hijos
                            NodoB aux = href.datos[i].hijoDer;
                            NodoB aux2 = href.datos[i].hijoIzq;
                            href.datos[i].hijoDer = href.datos[j].hijoDer;
                            href.datos[i].hijoIzq = href.datos[j].hijoIzq;
                            href.datos[j].hijoDer = aux;
                            href.datos[j].hijoIzq = aux2;
                        }
                        {//mover dato
                            ruta t = tmp.getDato();
                            href.datos[i].setDato(t2.getDato());
                            href.datos[j].setDato(t);
                        }

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    private void dividir(NodoB nd) {
        if (nd.padre == null) {
            ArbolB.contador++;
            //Si esta en la raiz
            nd.padre = new NodoB(ArbolB.contador);
        }
        //tomar nodo de m/2 
        ruta ins = nd.datos[2].getDato();
        //Agregarlo a su padre
        nd.padre.add(ins);
        ArbolB.contador++;
        //Crear nuedo arreglo de nodo
        NodoB nuevo = new NodoB(ArbolB.contador);
        //Llenando nuevo arreglo
        nuevo.add(nd.datos[3]);
        nuevo.add(nd.datos[4]);
        int pos = nd.getPos(ins, nd.padre);
        //Asignar nodo
        nd.padre.datos[pos].hijoDer = nuevo;
        nd.datos[2] = null;
        nd.datos[3] = null;
        nd.datos[4] = null;
        nd.size = 2;
        nd.padre.datos[pos].hijoIzq = nd;

        try {
            NodoB aux2 = nd.padre.datos[pos].hijoDer;
            nd.padre.datos[pos + 1].hijoIzq = aux2;
            nd.padre.datos[pos].hijoDer = null;
        } catch (Exception e) {
        }
        
        arreglarPuntero(nd.padre);
        if (nd.padre.size == 5) {
            //Si padre esta lleno dividir
            NodoB punt = nd.padre.datos[2].hijoIzq;
            ruta valor = nd.padre.datos[2].getDato();
            nd.padre.dividir(nd.padre);
            int posP = getPos(valor, nd.padre.padre);
            nd.padre.padre.datos[posP].hijoIzq.datos[nd.padre.padre.datos[posP].hijoIzq.size - 1].hijoDer = punt;

        }
    }

    private void arreglarPuntero(NodoB aux) {
        if (aux == null) {
            return;
        }
        for (int i = 0; i < aux.size; i++) {
            try {
                Nodo tmp = aux.datos[i];
                if (tmp != null) {
                    if (tmp.hijoIzq != null) {
                        tmp.hijoIzq.padre = aux;
                        arreglarPuntero(tmp.hijoIzq);

                    }
                    //if(i==aux.size-1){
                    if (tmp.hijoDer != null) {
                        if (i == aux.size - 1) {
                            tmp.hijoDer.padre = aux;
                            arreglarPuntero(tmp.hijoDer);
                        } else {
                            tmp.hijoDer = null;
                        }

                    }
                } else {
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void add(Nodo n) {
        this.size++;
        this.datos[this.size - 1] = n;
        this.datos[this.size - 1].key = size;
    }

    private int getPos(ruta ruta, NodoB aux) {
        for (int i = 0; i < aux.size; i++) {
            if (ruta.getClave() == aux.datos[i].getDato().getClave()) {
                return i;
            }
        }
        return 0;
    }

    void eliminar(NodoB aux, double clave1, int posPadre) {
        for (int i = 0; i < aux.size; i++) {
            Nodo tmp = aux.datos[i];
            if (tmp == null) {
                break;
            }
            double clave2 = tmp.getDato().getClave();
            if (clave1 == clave2) {
                if (ArbolB.valid) {
                    if (ArbolB.cnt == 1) {
                        eliminar(i, posPadre, aux, i, aux.padre);
                        arreglarPuntero(ArbolB.raiz);
                        return;
                    }
                    ArbolB.cnt++;
                } else {
                    eliminar(i, posPadre, aux, i, aux.padre);
                    arreglarPuntero(ArbolB.raiz);
                    return;

                }

            }
            if (clave1 < clave2) {
                if (aux.datos[i].hijoIzq != null) {
                    eliminar(aux.datos[i].hijoIzq, clave1, i);

                    if (aux.padre != null) {
                        if (aux.size < 2) {

                            unir2(i, posPadre, aux, aux.padre);

                        }
                    }
                    return;
                }

            }
            if (aux.size == i + 1) {
                if (aux.datos[i].hijoDer != null) {
                    eliminar(aux.datos[i].hijoDer, clave1, i);
                    if (aux.padre != null) {
                        if (aux.size < 2) {
                            unir2(i, posPadre, aux, aux.padre);
                        }
                    }
                    return;
                }

            }
        }
    }

    private void eliminar(int pos, int posPadre, NodoB punt, int finalDir, NodoB padre) {
        //Pos = finalDir posicion del elemento a eliminar
        //posPadre = posicion del padre/ nodoB padre=padre de punt
        int tamaño = punt.size;
        if (pos != tamaño - 1) {
            //Si el nodo a eliminar no es el ultimo del arreglo
            try {
                if (punt.datos[pos + 1].hijoIzq == null) {
                    //si no contiene hijo a la izquierda     | 
                    // moviendo los nodos hacia atras <---- [0][1][2] now [1][2]
                    boolean value = fix(pos, punt);
                    if (value) {
                        //prestar dato
                        prestar(padre, posPadre, finalDir, posPadre, punt);

                    }
                } else {
                    //Caso dos si el nodo a eliminar no esta hasta abajo
                    Nodo direc = punt.datos[pos + 1].hijoIzq.datos[0];
                    ruta key = null;
                    while (direc != null) {
                        if (direc.hijoIzq != null) {
                            direc = direc.hijoIzq.datos[0];
                        } else {
                            key = direc.getDato();
                            break;
                        }

                    }
                    ArbolB.cnt = 0;
                    ArbolB.valid = true;
                    punt.datos[pos].setDato(key);
                    try {
                        ArbolB.eliminar(key.getClave());
                        ArbolB.valid = false;
                        ArbolB.cnt = 0;
                    } catch (NullPointerException e) {
                    }

                }
            } catch (NullPointerException e) {

            }

        } else {

            if (punt.datos[pos].hijoIzq == null && punt.datos[pos].hijoDer == null) {
                boolean value = fix(pos, punt);
                if (value) {
                    try{
                    prestar(padre, posPadre, finalDir, posPadre, punt);
                    }catch(NullPointerException e){}
                  
                }
            } else {
                Nodo direc = punt.datos[pos].hijoDer.datos[0];
                ruta key = null;
                while (direc != null) {
                    if (direc.hijoIzq != null) {
                        direc = direc.hijoIzq.datos[0];
                    } else {
                        key = direc.getDato();
                        break;
                    }

                }
                ArbolB.cnt = 0;
                ArbolB.valid = true;
                punt.datos[pos].setDato(key);
                ArbolB.eliminar(key.getClave());
                ArbolB.valid = false;
                ArbolB.cnt = 0;
            }
        }
    }

    private boolean fix(int i, NodoB punt) {
        for (int j = i; j < punt.size; j++) {
            try {
                try {
                    punt.datos[j + 1].key = punt.datos[j + 1].key - 1;
                } catch (NullPointerException ex) {
                }
                punt.datos[j] = punt.datos[j + 1];
            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        punt.size -= 1;
        return punt.size < 2;
    }

    private boolean prestar(NodoB aux, int pos, int dir, int posPadre, NodoB href) {
        //Padre, posicion del padre, posicion del hijo, psocion del padre, referencia al hijo
        if (pos < aux.size - 1) {
            //si no se ha llegado al ultimo indice del arreglo del padre [][][][last]
            if (aux.datos[pos + 1].hijoIzq.size <= 2) {
                //Mientras [padre] sus hijos <2 seguir viendo quien tiene menos 
                boolean v = prestar(aux, pos + 1, dir, posPadre, href);
                if (v) {
                    delete3(aux, pos);
                }
                return v;
            } else {
                delete3(aux, pos);
                return true;
            }
        } else {
            try {
                if (aux.datos[pos].hijoIzq.size > 2) {
                    try {
                        ruta r1 = aux.datos[pos].hijoIzq.datos[0].getDato();
                        ruta r2 = aux.datos[pos].getDato();
                        fix(0, aux.datos[pos].hijoIzq);
                        aux.datos[pos].setDato(r1);
                        if (pos == aux.size - 1) {
                            aux.datos[pos].hijoDer.add(r2);
                        } else {
                            aux.datos[pos - 1].hijoIzq.add(r2);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }

                    return true;
                } else {
                    if (aux.datos[pos].hijoDer.size > 2) {
                        ruta r1 = aux.datos[pos].hijoDer.datos[0].getDato();
                        ruta r2 = aux.datos[pos].getDato();
                        fix(0, aux.datos[pos].hijoDer);
                        aux.datos[pos].setDato(r1);
                        aux.datos[pos].hijoIzq.add(r2);
                        return true;
                    } else {
                        prestarHiz(aux, posPadre, dir, posPadre, href);
                        return false;
                    }
                }
            } catch (NullPointerException e) {
            }

        }

        return false;
    }

    private boolean prestarHiz(NodoB aux, int pos, int dir, int posPadre, NodoB href) {
        if (pos > 0) {
            //si no se ha llegado al ultimo indice del arreglo del padre [][][][last]
            if (aux.datos[pos - 1].hijoIzq.size <= 2) {
                //Mientras [padre] sus hijos <2 seguir viendo quien tiene menos 
                boolean v = prestarHiz(aux, pos - 1, dir, posPadre, href);
                if (v) {
                    if (pos == posPadre) {
                        delete(aux, pos);
                        delete2(aux, pos);
                    } else {
                        delete(aux, pos);
                    }
                }
                return v;
            } else {
                delete(aux, pos);
                if (pos == posPadre && posPadre == aux.size - 1) {
                    delete2(aux, pos);
                }
                arreglarPuntero(aux);
                return true;
            }
        } else {

            try {
                if (aux.datos[pos].hijoIzq.size > 2) {

                } else {
                    unir(dir, posPadre, aux, href);
                }
            } catch (NullPointerException e) {
            }
        }
        return false;
    }

    private void delete(NodoB aux, int pos) {
        int posx = aux.datos[pos - 1].hijoIzq.size - 1;
        ruta izq = aux.datos[pos - 1].hijoIzq.datos[posx].getDato();
        ruta dad = aux.datos[pos - 1].getDato();
        aux.datos[pos].hijoIzq.add(dad);
        aux.datos[pos - 1].setDato(izq);
        aux.datos[pos - 1].hijoIzq.datos[posx] = null;
        aux.datos[pos - 1].hijoIzq.size -= 1;

    }

    private void delete2(NodoB aux, int pos) {
        //eliminar cuando es el ultimo nodo
        ruta dad = aux.datos[pos].getDato();
        int posx = aux.datos[pos].hijoIzq.size - 1;
        ruta izq = aux.datos[pos].hijoIzq.datos[posx].getDato();
        aux.datos[pos].setDato(izq);
        aux.datos[pos].hijoDer.add(dad);
        aux.datos[pos].hijoIzq.size -= 1;
        aux.datos[pos].hijoIzq.datos[posx] = null;
    }

    private void delete3(NodoB aux, int pos) {
        ruta r1 = aux.datos[pos + 1].hijoIzq.datos[0].getDato();
        ruta r2 = aux.datos[pos].getDato();
        fix(0, aux.datos[pos + 1].hijoIzq);
        aux.datos[pos].setDato(r1);
        aux.datos[pos].hijoIzq.add(r2);
    }

    private void unir(int pos, int posPadre, NodoB punt, NodoB href) {
        try {
            if (posPadre == punt.size - 1) {
                int opc = punt.datos[posPadre].hijoDer.size;
                ruta rta = punt.datos[posPadre].hijoDer.datos[0].getDato();
                ruta rt = punt.datos[posPadre].getDato();
                punt.datos[posPadre].hijoIzq.add(rta);
                punt.datos[posPadre].hijoIzq.add(rt);
                if (opc != 1) {
                    ruta tmp = punt.datos[posPadre].hijoDer.datos[1].getDato();
                    punt.datos[posPadre].hijoIzq.add(tmp);
                }
                int aux = posPadre - 1;
                try {
                    punt.datos[aux].hijoDer = punt.datos[posPadre].hijoIzq;
                    punt.size -= 1;
                    punt.datos[posPadre] = null;
                    arreglarPuntero(punt);
                } catch (ArrayIndexOutOfBoundsException e) {
                    ArbolB.raiz = punt.datos[0].hijoIzq;
                    punt.datos[0].hijoIzq.padre = null;
                }

            } else {
                pos = 0;
                //Obteniendo el dato en (0) donde se elimino
                ruta rta = href.datos[pos].getDato();
                //asignando el valor a su siguiente hermano
                punt.datos[posPadre + 1].hijoIzq.add(rta);
                //obteniendo el valor intermedio entre su hermano y este (padre) y asignarlo
                rta = punt.datos[posPadre].getDato();
                punt.datos[posPadre + 1].hijoIzq.add(rta);
                //mover <- para atras los nodos del padre
                fix(posPadre, punt);
                //configurar punteros
                arreglarPuntero(punt);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void unir2(int pos, int posPadre, NodoB punt, NodoB dad) {
        //Posicion del Nodo, posicion del padre, href del nodo, href del padre
        if (dad.size - 1 != posPadre) {
            Nodo href = punt.datos[0];
            ruta rta = dad.datos[posPadre].getDato();
            NodoB hijoDer = punt.datos[0].hijoDer;
            dad.datos[posPadre + 1].hijoIzq.add(href);
            fix(posPadre, dad);
            punt = dad.datos[posPadre].hijoIzq;
            punt.add(rta);
            ordenarDatos(true, punt);
            int getpos = getPos(rta, punt);
            punt.datos[getpos].hijoIzq = hijoDer;
            arreglarPuntero(punt.padre);
        } else {
            ruta rt = dad.datos[posPadre].getDato();
            NodoB href = dad.padre;
            //Obteniendo el Nodo donde size<2
            Nodo dt = punt.datos[0];
            NodoB hrefDer = null;
            int opc = 0;
            if (punt.equals(dad.datos[posPadre].hijoIzq)) {
                hrefDer = dad.datos[posPadre].hijoDer;
                opc = 0;
            } else {
                hrefDer = dad.datos[posPadre].hijoIzq;
                opc = 1;
            }

            //obtenniendo su -> a la derecha
            NodoB der = null;
            if (opc == 1) {
                der = hrefDer.datos[hrefDer.size - 1].hijoDer;
            } else {
                der = dt.hijoDer;
            }
            //A su NodoB hermanod pasar su valor
            hrefDer.add(dt);
            //Ordenar los datos
            ordenarDatos(true, hrefDer);
            //Agregar la ruta del padre
            hrefDer.add(rt);
            //Obtener ubicacion
            int getPos = getPos(rt, hrefDer);
            //Asignar hjo izq
            hrefDer.datos[getPos].hijoIzq = der;
            //Reasignar el padre
            if (posPadre == 0) {
                hrefDer.padre = href;
                if (href == null) {
                    //Si el padre era la raiz re asignar valor de la raiz
                    ArbolB.raiz = hrefDer;
                }
            } else {
                dad.datos[posPadre - 1].hijoDer = hrefDer;
                dad.datos[posPadre] = null;
                dad.size -= 1;
            }

        }
    }

}
