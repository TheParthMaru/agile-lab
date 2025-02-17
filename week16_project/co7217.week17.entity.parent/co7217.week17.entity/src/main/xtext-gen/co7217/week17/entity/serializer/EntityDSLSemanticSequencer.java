/*
 * generated by Xtext 2.32.0
 */
package co7217.week17.entity.serializer;

import co7217.week17.entity.entityDSL.DataType;
import co7217.week17.entity.entityDSL.Domainmodel;
import co7217.week17.entity.entityDSL.Entity;
import co7217.week17.entity.entityDSL.EntityDSLPackage;
import co7217.week17.entity.entityDSL.Literal;
import co7217.week17.entity.services.EntityDSLGrammarAccess;
import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class EntityDSLSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private EntityDSLGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == EntityDSLPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case EntityDSLPackage.DATA_TYPE:
				sequence_DataType(context, (DataType) semanticObject); 
				return; 
			case EntityDSLPackage.DOMAINMODEL:
				sequence_Domainmodel(context, (Domainmodel) semanticObject); 
				return; 
			case EntityDSLPackage.ENTITY:
				sequence_Entity(context, (Entity) semanticObject); 
				return; 
			case EntityDSLPackage.LITERAL:
				sequence_Literal(context, (Literal) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractElement returns DataType
	 *     Type returns DataType
	 *     DataType returns DataType
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_DataType(ISerializationContext context, DataType semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EntityDSLPackage.Literals.TYPE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EntityDSLPackage.Literals.TYPE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getDataTypeAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Domainmodel returns Domainmodel
	 *
	 * Constraint:
	 *     elements+=AbstractElement+
	 * </pre>
	 */
	protected void sequence_Domainmodel(ISerializationContext context, Domainmodel semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     AbstractElement returns Entity
	 *     Type returns Entity
	 *     Entity returns Entity
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_Entity(ISerializationContext context, Entity semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EntityDSLPackage.Literals.TYPE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EntityDSLPackage.Literals.TYPE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getEntityAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Literal returns Literal
	 *
	 * Constraint:
	 *     value=ID
	 * </pre>
	 */
	protected void sequence_Literal(ISerializationContext context, Literal semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, EntityDSLPackage.Literals.LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EntityDSLPackage.Literals.LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getLiteralAccess().getValueIDTerminalRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
}
