package com.config.tool.patchMgr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.config.tool.constent.IConfigToolConstents;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PatchListPresenter implements IPatchPresenter {

	private final IPatchListView view;

	@Override
	public void getPatchList(DefaultListModel<PatchStatusCheckBox> patchListModel) {
		// TODO Auto-generated method stub
		view.getPatchList(getPatchlist(patchListModel));

	}

	private DefaultListModel<PatchStatusCheckBox> getPatchlist(DefaultListModel<PatchStatusCheckBox> patchListModel) {
		ArrayList<String> patchPaths = new ArrayList<>();
		patchPaths.add("C:\\\\xstore\\\\lib\\\\patch");
		patchPaths.add("C:\\\\xstore\\\\lib\\\\patch\\\\disabled");

		for (String directoryPath : patchPaths) {
			File directory = new File(directoryPath);

			List<File> jarFiles = getPatchFiles(directory);
			if (jarFiles != null) {
				for (File file : jarFiles) {
					if (directory.toString().endsWith("disabled")) {
						patchListModel.addElement(new PatchStatusCheckBox(file, false));
					} else {
						patchListModel.addElement(new PatchStatusCheckBox(file, true));

					}
				}
			}
		}
		return patchListModel;
	}

	private List<File> getPatchFiles(File directory) {
		List<File> jarFiles = new ArrayList<>();
		File[] files = directory.listFiles();

		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
					jarFiles.add(file);
				}
			}
		}

		return jarFiles;
	}

	@Override
	public boolean checkXstoreRunning() {
		boolean isXstoreRunning = false;
		// Specify the path to your file
		String filePath = IConfigToolConstents.XSTORE_ANCHOR_FILE_PATH;
		;

		// Use the Paths and Files classes to check file existence
		Path path = Paths.get(filePath);

		if (Files.exists(path)) {
			isXstoreRunning = true;
		}
		return isXstoreRunning;
	}

	@Override
	public void movePatches(DefaultListModel<PatchStatusCheckBox> patchListModel) {

		DefaultListModel<PatchStatusCheckBox> fileList = patchListModel;

		for (Object file : fileList.toArray()) {
			if (file instanceof PatchStatusCheckBox) {
				PatchStatusCheckBox fileCheckBox = (PatchStatusCheckBox) file;
				if (!fileCheckBox.selected) {
					try {
						if (new File(fileCheckBox.file.getPath()).exists()
								&& !(new File("C:\\xstore\\lib\\patch\\disabled\\" + fileCheckBox.getFile().getName())
										.exists())) {
							Files.move(Paths.get(fileCheckBox.file.getPath()),
									Paths.get("C:\\xstore\\lib\\patch\\disabled\\" + fileCheckBox.getFile().getName()));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						if ((new File("C:\\xstore\\lib\\patch\\disabled\\" + fileCheckBox.getFile().getName()).exists())
								&& !(new File("C:\\xstore\\lib\\patch" + fileCheckBox.getFile().getName()).exists())) {
							Files.move(Paths.get(fileCheckBox.file.getPath()),
									Paths.get("C:/xstore/lib/patch/" + fileCheckBox.getFile().getName()));
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public void killStore() {

		try {
			Files.delete(Paths.get(IConfigToolConstents.XSTORE_ANCHOR_FILE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void handlePatchFiles(PatchListUI patchListUI, DefaultListModel<PatchStatusCheckBox> patchListModel) {

		if (checkXstoreRunning()) {
			int result = view.showXstoreRestartWarning(patchListUI,
					"Patches cann't be moved while Xstore Running. Please first close Xstore and then proceed. Do you want to close Xstore?",
					"Confirmation");
			// Handle the user's choice
			if (result == JOptionPane.YES_OPTION) {
				killStore();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				movePatches(patchListModel);
				view.showPatchesDeployed();

			} else {
				System.out.println("User clicked No or closed the dialog.");
			}
		} else {
			movePatches(patchListModel);
			view.showPatchesDeployed();

		}
		// TODO Auto-generated method stub
		// return null;
	}

}
