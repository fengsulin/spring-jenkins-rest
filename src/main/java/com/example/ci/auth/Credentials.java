package com.example.ci.auth;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: FSL
 * @date: 2023/3/20
 * @description: 凭证基类
 */
public class Credentials {
    public final String identity;
    public final String credential;

    public Credentials(String identity, String credential) {
        this.identity = identity;
        this.credential = credential;
    }

    public Builder<? extends Credentials> toBuilder(){
        return new Builder<Credentials>().identity(identity).credential(credential);
    }

    @Override
    public String toString() {
        return "[identity=" + identity + ", credential=" + credential + "]";
    }

    @Override
    public int hashCode() {

        return hashCode(identity, credential);
    }
    public static  int hashCode( Object ... objects){
        return Arrays.hashCode(objects);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Credentials))
            return false;
        Credentials other = (Credentials) obj;
        if (!Objects.equals(identity, other.identity))
            return false;
        if (!Objects.equals(credential, other.credential))
            return false;
        return true;
    }
    public static class Builder<T extends Credentials>{
        protected String identity;
        protected String credential;

        public Builder<T> identity(String identity){
            this.identity = identity;
            return this;
        }

        public Builder<T> credential(String credential){
            this.credential = credential;
            return this;
        }

        public T build(){
            return (T)new Credentials(identity,credential);
        }
    }
}
