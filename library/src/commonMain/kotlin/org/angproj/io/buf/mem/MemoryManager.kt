package org.angproj.io.buf.mem

import org.angproj.io.buf.seg.Segment

public interface MemoryManager<S: Segment<S>> {
    public fun allocate(size: Int): S

    public fun recycle(segment: S)
}