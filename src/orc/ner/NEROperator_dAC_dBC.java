package orc.ner;

import beast.base.util.Randomizer;
import orc.operators.MetaNEROperator;

public class NEROperator_dAC_dBC extends MetaNEROperator {

	@Override
	protected double proposalRates(double rWindowSize, double ta, double tb, double tc, double td, double te, 
										double ra, double rb, double rc, double rd) {

		// Random proposals
		double r_alpha = 0;
		double r_beta = 0;
		double r_gamma = 0;
		double r_delta = 0;
		double r_epsilon = this.getRandomWalkStepSize(rWindowSize);


		// Propose new rates + times
		this.rap = -(r_delta*r_epsilon - r_epsilon*r_gamma - r_epsilon*rc + r_epsilon*rd - ra*ta + r_gamma*tc + r_delta*td - r_gamma*td - r_delta*te + ra*td - rc*td + rc*te)/(ta - te);
		this.rbp = (tc*(r_gamma + rc) - rb*tb + rb*td - rc*tc + rc*te - rd*td + rd*te - (r_gamma + rc)*(r_epsilon + td))/(r_epsilon - tb + td);
		this.rcp = r_gamma + rc;
		this.rdp = r_delta + rd;
		this.tdp = r_epsilon + td;


		// Jacobian determinant
		double JD = -((ta - td)*(tb - td))/((ta - te)*(r_epsilon - tb + td));
		if (JD <= 0) return Double.NEGATIVE_INFINITY;
		return Math.log(JD);

	}

}
