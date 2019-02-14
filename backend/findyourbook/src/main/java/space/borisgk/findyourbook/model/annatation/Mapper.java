package space.borisgk.findyourbook.model.annatation;

import java.util.function.Function;

final class Mapper {
    static final Function
            dataBaseObjectToModel = new Function() {
        @Override
        public Object apply(Object o) {
            return o.toString();
        }
            },
            modelToDataBase = new Function() {
        @Override
        public Object apply(Object o) {
            return o.toString();
        }
    };
}
