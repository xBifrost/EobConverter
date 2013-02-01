package org.eob.file.inf;

import org.eob.file.inf.commands.*;
import org.eob.file.inf.commands.condition.TermLeaf;
import org.eob.file.inf.commands.condition.TermNode;
import org.eob.file.inf.commands.condition.expression.*;

/**
 * User: Bifrost
 * Date: 12.1.2013
 * Time: 22:45
 */
public interface CommandVisitor {
    void visit(DamageCommand damageCommand);

    void visit(LauncherCommand launcherCommand);

    void visit(GiveXpCommand giveXpCommand);

    void visit(ScriptEndCommand scriptEndCommand);

    void visit(IdentifyAllItemsCommand identifyAllItemsCommand);

    void visit(StealSmallItemsCommand stealSmallItemsCommand);

    void visit(TeleportCommand teleportCommand);

    void visit(JumpCommand jumpCommand);

    void visit(ReturnCommand returnCommand);

    void visit(CallCommand callCommand);

    void visit(CreateMonsterCommand createMonsterCommand);

    void visit(EncounterCommand encounterCommand);

    void visit(ClearFlagCommand clearFlagCommand);

    void visit(TurnCommand turnCommand);

    void visit(ChangeWallCommand changeWallCommand);

    void visit(NewItemCommand newItemCommand);

    void visit(SoundCommand soundCommand);

    void visit(OpenDoorCommand openDoorCommand);

    void visit(CloseDoorCommand closeDoorCommand);

    void visit(SetWallCommand setWallCommand);

    void visit(SetFlagCommand setFlagCommand);

    void visit(MessageCommand messageCommand);

    void visit(ConditionCommand conditionCommand);

    void visit(ChangeLevelCommand changeLevelCommand);

    void visit(GraphicsDataCommand graphicsDataCommand);

    void visit(ItemConsumeCommand itemConsumeCommand);

    void visit(WallMappingCommand wallMappingCommand);

    void visit(MiscValueLeaf miscValueLeaf);

    void visit(MonsterCountLeaf monsterCountLeaf);

    void visit(PartyPositionCheckLeaf partyPositionCheckLeaf);

    void visit(PartyInventoryCountLeaf partyInventoryCountLeaf);

    void visit(MazeItemCountLeaf mazeItemCountLeaf);

    void visit(MazeFlagLeaf mazeFlagLeaf);

    void visit(MiscTriggerFlagLeaf miscTriggerFlagLeaf);

    void visit(MazeWallSideLeaf mazeWallSideLeaf);

    void visit(MazeWallTypeLeaf mazeWallTypeLeaf);

    void visit(PartyContainsAlignmentLeaf partyContainsAlignmentLeaf);

    void visit(PartyContainsRaceLeaf partyContainsRaceLeaf);

    void visit(PartyItemTypeLeaf partyItemTypeLeaf);

    void visit(PartyItemSubTypeLeaf partyItemSubTypeLeaf);

    void visit(PartyItemNameIdLeaf partyItemNameIdLeaf);

    void visit(PartyItemUniqueNameIdLeaf partyItemUniqueNameIdLeaf);

    void visit(PartyContainsClassLeaf partyContainsClassLeaf);

    void visit(MiscRollDiceLeaf miscRollDiceLeaf);

    void visit(MouseItemTypeLeaf mouseItemTypeLeaf);

    void visit(PartyDirectionLeaf partyDirectionLeaf);

    void visit(MiscFlagLeaf miscFlagLeaf);

    void visit(PartyVisibleLeaf partyVisibleLeaf);

    void visit(ListNode listNode);

    void visit(TermLeaf termLeaf);

    void visit(TermNode termNode);
}
