package personal.viewcontroller.util;

import personal.dto.CandidateReadOnlyDTO;
import personal.dto.CandidatesWithVotesReadOnlyDTO;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * A Utility class which helps in rendering.
 *
 * @author Michail E. Koutrakis.
 */
public class RenderingUtil {
    private RenderingUtil() {}

    /**
     * Render a {@link List} of  {@link CandidateReadOnlyDTO} to the {@link DefaultTableModel} given.
     * @param candidateReadOnlyDTOs     A List with the DTOs to be rendered
     * @param tableModel                The model used by the table
     */
    public static <T extends CandidateReadOnlyDTO> void renderCandidatesModelTable(List<T> candidateReadOnlyDTOs,
                                             DefaultTableModel tableModel) {
        Vector<String> vector;
        int tableSize = tableModel.getColumnCount();

        cleanModelTable(tableModel);

        for (T dto : candidateReadOnlyDTOs) {
            vector = new Vector<>(tableSize);
            vector.add(Integer.toString(dto.getCid()));
            vector.add(dto.getFirstname());
            vector.add(dto.getLastname());
            if (dto instanceof CandidatesWithVotesReadOnlyDTO) {
                int numberOfVotes =((CandidatesWithVotesReadOnlyDTO) dto).getTotalVotes();
                vector.add(Integer.toString(numberOfVotes));
            }

            tableModel.addRow(vector);
        }
    }

    /**
     * Remove all rows from the {@link DefaultTableModel} given
     *
     * @param tableModel    The table model from where the rows will be removed.
     */
    public static void cleanModelTable(DefaultTableModel tableModel) {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }
}
