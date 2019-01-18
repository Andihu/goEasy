package com.xy.rcs.goeasy.module;

/**
 * <p>文件描述：<p>
 * <p>作者：hujian<p>
 * <p>创建时间：2018/12/13/013<p>
 * <p>更改时间：2018/12/13/013<p>
 * <p>版本号：1<p>
 */
public interface IAddCartListener {
    void success();
    void fail();
    abstract class Listener implements IAddCartListener{
        @Override
        public void success() {

        }

        @Override
        public void fail() {

        }
    }
}
