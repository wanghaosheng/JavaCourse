# JavaCourse
1.2 course001中的MyclassLoader

1.3 ![Image text](https://raw.githubusercontent.com/wanghaosheng/JavaCourse/main/course001/src/main/resources/%E4%BD%9C%E4%B8%9A2.jpg)

2.4 1).从整体上看:堆内存越小，每次GC花费的时间越少，但是GC的频率越高;堆内存越大，每次GC的花费的时间越多，但是GC的频率越低
    2).从局部上看：young区的对象大量，高频回收，少量存活，应该分配较小的内存，用效率高的copying算法；old区对象少，存活高，回收少应该采用mc或者ms算法
       尽量减少Fullgc的次数
