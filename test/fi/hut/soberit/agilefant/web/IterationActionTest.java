package fi.hut.soberit.agilefant.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

import fi.hut.soberit.agilefant.business.IterationBusiness;
import fi.hut.soberit.agilefant.exception.ObjectNotFoundException;
import fi.hut.soberit.agilefant.model.Iteration;
import fi.hut.soberit.agilefant.transfer.IterationTO;

/**
 * Struts test case extends jUnit 3's <code>TestCase</code>.
 * Therefore, the tests must be written in jUnit 3 style.
 */
public class IterationActionTest {
    
    // Class under test
    IterationAction iterationAction;
    
    // Dependencies
    IterationBusiness iterationBusiness;
    
    @Before
    public void setUp() {
        iterationAction = new IterationAction();
        iterationAction.setIterationId(1);
        iterationBusiness = createMock(IterationBusiness.class);
        iterationAction.setIterationBusiness(iterationBusiness);
    }
    
    private void verifyAll() {
        verify(iterationBusiness);
    }

    private void replayAll() {
        replay(iterationBusiness);
    }
    
    @Test
    public void testDelete_success() {
        iterationBusiness.delete(1);
        expect(iterationBusiness.retrieve(1)).andReturn(new Iteration());
        replayAll();
        
        assertEquals(Action.SUCCESS, iterationAction.delete());
        
        verifyAll();    
    }

    
    @Test(expected = ObjectNotFoundException.class)
    public void testDelete_noSuchIteration() {
        iterationAction.setIterationId(-1);
        expect(iterationBusiness.retrieve(-1)).andThrow(new ObjectNotFoundException());
        replayAll();
        
        iterationAction.delete();
        
        verifyAll();    
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void testDelete_forbidden() {
        iterationBusiness.delete(1);
        expectLastCall().andThrow(new ConstraintViolationException(null, null, null));
        expect(iterationBusiness.retrieve(1)).andReturn(new Iteration());
        replayAll();
        
        iterationAction.delete();
        
        verifyAll(); 
    }
    
    @Test
    public void testStore() {
        Iteration iter = new Iteration();
        Iteration iter2 = new Iteration();
        expect(iterationBusiness.store(1, 2, iter)).andReturn(iter2);
        replayAll();
        iterationAction.setIteration(iter);
        iterationAction.setIterationId(1);
        iterationAction.setParentBacklogId(2);
        iterationAction.store();
        assertEquals(IterationTO.class, iterationAction.getIteration().getClass());
        verifyAll();
    }
}
