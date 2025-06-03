package com.loop.framework.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.loop.framework.core.Loop;
import com.loop.framework.core.ScreenshotUtil;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

public class StepLogger implements EventListener {

	private static ThreadLocal<ExtentTest> currentStepNode = new ThreadLocal<>();

	public static ExtentTest getCurrentStepNode() {
		return currentStepNode.get();
	}

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		publisher.registerHandlerFor(TestStepFinished.class, this::logStep);
	}

	private void logStep(TestStepFinished event) {
		if (!(event.getTestStep() instanceof PickleStepTestStep))
			return;

		PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
		String stepText = step.getStep().getKeyword() + step.getStep().getText();
		ExtentTest scenarioNode = Loop.reporter.getCurrentTest();

		try {
			ExtentTest gherkinStepNode;

			if (event.getResult().getStatus() == Status.PASSED) {
				gherkinStepNode = scenarioNode.createNode(stepText);
			} else {
				String screenshot = ScreenshotUtil.captureScreenshotAsBase64();
				gherkinStepNode = scenarioNode.createNode(stepText)
						.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build())
						.fail(event.getResult().getError());
			}

			currentStepNode.set(gherkinStepNode);

			// ✅ Flush all methods logged under this step
			Loop.reporter.flushStepLogsTo(gherkinStepNode);

		} catch (Exception e) {
			scenarioNode.warning("⚠ Failed to log step: " + stepText);
		}
	}
}
