package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController
{
    private TransferDAO transferDao;

    public TransferController(TransferDAO transferDao) {
        this.transferDao = transferDao;
    }


    @RequestMapping(path = "/create_transfer", method = RequestMethod.POST)
    public void createTransaction(@RequestBody Transfer transfer, Principal principal){

        transfer.setTransferStatusId("Approved");
        transferDao.createTransfer(transfer, principal.getName());

       // return transferDao.getTransferByUserName(principal.getName());
    }

    @RequestMapping(path = "/allTransfers_by_username", method = RequestMethod.GET)
    public List<Transfer> getAllTransfersByUsername(Principal principal)
    {
        return transferDao.getAllTransfersByUsername(principal.getName());
    }


    @RequestMapping(path = "/allTransfers_by_transferId/{transfer_id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transfer_id){
        return transferDao.getTransferByTransferId(transfer_id);
    }
}
