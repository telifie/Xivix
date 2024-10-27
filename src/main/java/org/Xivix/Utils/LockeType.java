package org.Xivix.Utils;

/**
 * Primarily for object statuses and can mean whatever you want but
 * some objects have specific LockeTypes they abide by.
 *
 * <a href='https://telifie.com/projects/Xivix/documention'>See Documention</a>
 */
public enum LockeType {
    APPROVED, //Approved by whoever necessary
    ARCHIVED, //Like delete but still in system and can't be used AT ALL
    BLOCKED, //BLocked for forseeable future
    DRAFT, //Object is in draft stage and can go througn NO process
    DELINQUENT, //Past due/delinquent status (mainly customers, orders, payments, etc.)
    ERRORED, //Errorer out in processing, saving, transmission, etc.
    NEW, //Brand new, just made, not touched
    OPEN, //Just open, typically first stage
    PENDING, //From pending, pending first major action
    REMOVED, //Remove, not archived but still can't be used
    SUSPENDED, //Blocked, but intent is to unblock
}