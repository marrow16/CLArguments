package com.adeptions;

import com.adeptions.arguments.*;

public class Application {
	/**
	 * Main startup
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ArgumentDefinitions argumentDefinitions = new ArgumentDefinitions(
				new StringArgumentDefinition(new String[] {"say", "s"}, "What to say").makeMandatory().addValidator((value, argument) -> {
					System.out.println("Custom validator called with: " + value);
					if (((String)value).contains(" ")) {
						throw new ArgsParsingException(ArgsParsingExceptionReason.UNDEFINED, "Cannot contain spaces?");
					}
					return value;
				}),
				new InformationalArgumentDefinition(new String[] {"version", "v"}, "Show version"),
				new InformationalArgumentDefinition(new String[] {"help", "h"}, "Show this help")
		);
		ArgsParsingOptions argsParsingOptions = new ArgsParsingOptions();
		argsParsingOptions.setArgsParsingExceptionHandler(new IArgsParsingExceptionHandler() {
			@Override
			public ArgsParsingException handle(ArgsParsingException argsParsingException) throws ArgsParsingException {
				System.err.println("Custom exception handler found:- " + argsParsingException);
				return argsParsingException;
			}
		});
		try {
			Arguments arguments = argumentDefinitions.parseArgs(args, argsParsingOptions);
			printVersionIfRequested(arguments);
			if (arguments.hasParsingExceptions()) {
				for (ArgsParsingException argsParsingException: arguments.getParsingExceptions()) {
					System.err.println(argsParsingException.getMessage());
				}
			} else if (arguments.getSpecifiedCount() == 0 || arguments.get("help").isSpecified()) {
				System.out.println("Help:-");
				System.out.println(argumentDefinitions.getHelp(argsParsingOptions));
			} else if (!arguments.hasSpecifiedInformationals()) {
				System.out.println("Say... " + arguments.get("say").getValue());
			}
		} catch (ArgsParsingException e) {
			e.printStackTrace();
		}
	}

	private static void printVersionIfRequested(Arguments arguments) {
		if (arguments.get("version").isSpecified()) {
			System.out.println("Version: 1.0-SNAPSHOT");
		}
	}
}
