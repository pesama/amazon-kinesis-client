package com.amazonaws.services.kinesis.clientlibrary.lib.worker;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.KinesisClientLibDependencyException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IPreparedCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ExtendedSequenceNumber;

/**
 * A special IPreparedCheckpointer that does nothing, which can be used when preparing a checkpoint at the current
 * checkpoint sequence number where it is never necessary to do another checkpoint.
 * This simplifies programming by preventing application developers from having to reason about whether
 * their application has processed records before calling prepareCheckpoint
 *
 * Here's why it's safe to do nothing:
 * The only way to checkpoint at current checkpoint value is to have a record processor that gets
 * initialized, processes 0 records, then calls prepareCheckpoint(). The value in the table is the same, so there's
 * no reason to overwrite it with another copy of itself.
 */
public class DoesNothingPreparedCheckpointer implements IPreparedCheckpointer {

    private final ExtendedSequenceNumber sequenceNumber;

    /**
     * Constructor.
     * @param sequenceNumber the sequence number value
     */
    public DoesNothingPreparedCheckpointer(ExtendedSequenceNumber sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ExtendedSequenceNumber getPendingCheckpoint() {
        return sequenceNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkpoint()
            throws KinesisClientLibDependencyException, InvalidStateException, ThrottlingException, ShutdownException,
            IllegalArgumentException {
        // This method does nothing
    }

}

